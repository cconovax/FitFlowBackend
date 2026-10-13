package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.membership.application.services.UserGymMembershipService;
import com.conovax.fitflow.shared.application.services.RoleService;
import com.conovax.fitflow.scheduling.application.dto.PurchaseRequest;
import com.conovax.fitflow.scheduling.application.dto.ScheduleRequest;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.*;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.*;
import com.conovax.fitflow.shared.domain.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulingService {
    private static final List<String> ACTIVE_RESERVATION_STATUSES = List.of("CONFIRMED", "WAITLISTED", "ATTENDED", "NO_SHOW");

    private final ClassScheduleJpaRepository scheduleRepository;
    private final ClassSessionJpaRepository sessionRepository;
    private final ClassReservationJpaRepository reservationRepository;
    private final ClassPurchaseJpaRepository purchaseRepository;
    private final UserGymMembershipService membershipService;
    private final RoleService roleService;

    @Transactional(readOnly = true)
    public List<ClassScheduleJpaEntity> listSchedules(Long gymId) {
        return scheduleRepository.findAllByGymIdOrderByWeekdayAscStartTimeAsc(gymId);
    }

    @Transactional
    public ClassScheduleJpaEntity createSchedule(Long gymId, ScheduleRequest request) {
        validateTime(request);
        LocalDateTime now = LocalDateTime.now();
        return scheduleRepository.save(ClassScheduleJpaEntity.builder()
                .gymId(gymId).name(request.name()).description(request.description())
                .weekday(request.weekday()).startTime(request.startTime()).endTime(request.endTime())
                .capacity(request.capacity()).status(request.status() == null ? "ACTIVE" : request.status())
                .createdAt(now).updatedAt(now).build());
    }

    @Transactional
    public ClassScheduleJpaEntity updateSchedule(Long gymId, Long scheduleId, ScheduleRequest request) {
        validateTime(request);
        ClassScheduleJpaEntity schedule = scheduleRepository.findByIdAndGymId(scheduleId, gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado"));
        schedule.setName(request.name());
        schedule.setDescription(request.description());
        schedule.setWeekday(request.weekday());
        schedule.setStartTime(request.startTime());
        schedule.setEndTime(request.endTime());
        schedule.setCapacity(request.capacity());
        schedule.setStatus(request.status() == null ? schedule.getStatus() : request.status());
        schedule.setUpdatedAt(LocalDateTime.now());
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void deleteSchedule(Long gymId, Long scheduleId) {
        ClassScheduleJpaEntity schedule = scheduleRepository.findByIdAndGymId(scheduleId, gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado"));
        schedule.setStatus("INACTIVE");
        schedule.setUpdatedAt(LocalDateTime.now());
        scheduleRepository.save(schedule);
    }

    @Transactional(readOnly = true)
    public List<ClassSessionJpaEntity> listSessions(Long gymId, LocalDate from, LocalDate to) {
        LocalDate start = from == null ? LocalDate.now() : from;
        LocalDate end = to == null ? start.plusWeeks(4) : to;
        if (end.isBefore(start) || end.isAfter(start.plusWeeks(4))) {
            throw new IllegalArgumentException("La ventana de sesiones debe ser de máximo cuatro semanas");
        }
        return sessionRepository.findAllByGymIdAndStartsAtBetweenOrderByStartsAtAsc(
                gymId, start.atStartOfDay(), end.plusDays(1).atStartOfDay().minusNanos(1));
    }

    @Transactional
    public ClassReservationJpaEntity reserve(Long gymId, Long sessionId, Long userGymId, String source) {
        ClassSessionJpaEntity session = sessionRepository.findByIdAndGymId(sessionId, gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada"));
        validateUserGymBelongsToGym(gymId, userGymId);
        if (!"SCHEDULED".equals(session.getStatus())) throw new IllegalArgumentException("La sesión no está disponible");
        reservationRepository.findByGymIdAndSessionIdAndUserGymIdAndStatusIn(gymId, sessionId, userGymId, ACTIVE_RESERVATION_STATUSES)
                .ifPresent(existing -> { throw new IllegalArgumentException("El usuario ya tiene una reserva activa"); });

        boolean hasMembership = membershipService.hasActiveMembership(gymId, userGymId, LocalDate.now());
        boolean hasPaidPurchase = purchaseRepository.existsByGymIdAndSessionIdAndUserGymIdAndStatus(
            gymId, sessionId, userGymId, "PAID");
        if (!hasMembership && !hasPaidPurchase && !"MANUAL".equals(source)) {
            throw new IllegalArgumentException("Se requiere una membresía activa o una compra PAID");
        }

        long confirmed = reservationRepository.countByGymIdAndSessionIdAndStatusIn(gymId, sessionId, List.of("CONFIRMED", "ATTENDED"));
        String status = confirmed < session.getCapacity() ? "CONFIRMED" : "WAITLISTED";
        Integer position = "WAITLISTED".equals(status)
                ? (int) reservationRepository.countByGymIdAndSessionIdAndStatusIn(gymId, sessionId, List.of("WAITLISTED")) + 1 : null;
        return reservationRepository.save(ClassReservationJpaEntity.builder()
                .gymId(gymId).sessionId(sessionId).userGymId(userGymId).status(status)
                .source(source == null ? "ONLINE" : source).waitlistPosition(position)
                .reservedAt(LocalDateTime.now()).build());
    }

    @Transactional
    public void cancelReservation(Long gymId, Long reservationId) {
        ClassReservationJpaEntity reservation = reservationRepository.findByIdAndGymId(reservationId, gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        if ("CANCELLED".equals(reservation.getStatus())) return;
        reservation.setStatus("CANCELLED");
        reservation.setWaitlistPosition(null);
        reservationRepository.save(reservation);
        promoteNext(gymId, reservation.getSessionId());
    }

    @Transactional
    public ClassPurchaseJpaEntity createManualPurchase(Long gymId, PurchaseRequest request, Long currentUserGymId) {
        ClassSessionJpaEntity session = sessionRepository.findByIdAndGymId(request.sessionId(), gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada"));
        validateUserGymBelongsToGym(gymId, request.userGymId());
        validateStaffUserGym(gymId, currentUserGymId);
        ClassPurchaseJpaEntity purchase = purchaseRepository.save(ClassPurchaseJpaEntity.builder()
                .gymId(gymId).sessionId(session.getId()).userGymId(request.userGymId())
                .sellerUserGymId(currentUserGymId).amount(request.amount())
                .paymentMethod(request.paymentMethod()).status("PAID").source("MANUAL")
                .purchasedAt(LocalDateTime.now()).notes(request.notes()).build());
        ClassReservationJpaEntity reservation = reserve(gymId, session.getId(), request.userGymId(), "MANUAL");
        purchase.setReservationId(reservation.getId());
        return purchaseRepository.save(purchase);
    }

    @Transactional
    public ClassReservationJpaEntity markAttendance(Long gymId, Long reservationId, Long staffUserGymId, boolean attended) {
        ClassReservationJpaEntity reservation = reservationRepository.findByIdAndGymId(reservationId, gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        validateStaffUserGym(gymId, staffUserGymId);
        reservation.setStatus(attended ? "ATTENDED" : "NO_SHOW");
        reservation.setCheckedInAt(LocalDateTime.now());
        reservation.setCheckedInByUserGymId(staffUserGymId);
        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public List<ClassReservationJpaEntity> listReservationsForSession(Long gymId, Long sessionId) {
        sessionRepository.findByIdAndGymId(sessionId, gymId).orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada"));
        return reservationRepository.findAllByGymIdAndSessionIdOrderByReservedAtAsc(gymId, sessionId);
    }

    @Transactional(readOnly = true)
    public List<ClassReservationJpaEntity> listReservationsForUserGym(Long gymId, Long userGymId, Long requesterUserGymId) {
        validateUserGymBelongsToGym(gymId, userGymId);
        if (!userGymId.equals(requesterUserGymId)) {
            validateStaffUserGym(gymId, requesterUserGymId);
        }
        return reservationRepository.findAllByGymIdAndUserGymIdOrderByReservedAtDesc(gymId, userGymId);
    }

    @Transactional
    public int materializeWindow(Long gymId, LocalDate from) {
        int created = 0;
        for (ClassScheduleJpaEntity schedule : scheduleRepository.findAllByGymIdOrderByWeekdayAscStartTimeAsc(gymId)) {
            if (!"ACTIVE".equals(schedule.getStatus())) continue;
            for (int day = 0; day < 28; day++) {
                LocalDate date = from.plusDays(day);
                if (date.getDayOfWeek().getValue() != schedule.getWeekday()) continue;
                LocalDateTime starts = LocalDateTime.of(date, schedule.getStartTime());
                if (sessionRepository.existsByGymIdAndScheduleIdAndStartsAt(gymId, schedule.getId(), starts)) continue;
                sessionRepository.save(ClassSessionJpaEntity.builder().gymId(gymId).scheduleId(schedule.getId())
                        .startsAt(starts).endsAt(LocalDateTime.of(date, schedule.getEndTime()))
                        .capacity(schedule.getCapacity()).status("SCHEDULED").createdAt(LocalDateTime.now()).build());
                created++;
            }
        }
        return created;
    }

    private void promoteNext(Long gymId, Long sessionId) {
        ClassReservationJpaEntity next = reservationRepository
                .findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(gymId, sessionId, "WAITLISTED")
                .stream().findFirst().orElse(null);
        if (next == null) return;
        next.setStatus("CONFIRMED");
        next.setWaitlistPosition(null);
        reservationRepository.save(next);
    }

    private void validateTime(ScheduleRequest request) {
        if (!request.endTime().isAfter(request.startTime())) throw new IllegalArgumentException("La hora final debe ser posterior a la inicial");
    }

    private void validateUserGymBelongsToGym(Long gymId, Long userGymId) {
        if (!membershipService.userGymBelongsToGym(gymId, userGymId)) {
            throw new ResourceNotFoundException("El usuario no pertenece al gimnasio");
        }
    }

    private void validateStaffUserGym(Long gymId, Long userGymId) {
        if (!roleService.isActiveStaffUserGym(gymId, userGymId)) {
            throw new AccessDeniedException("El usuario no tiene un rol staff en este gimnasio");
        }
    }
}
