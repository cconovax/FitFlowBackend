package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.gym.application.dto.response.UserGymContact;
import com.conovax.fitflow.gym.application.services.UserGymUsersService;
import com.conovax.fitflow.membership.application.services.UserGymMembershipService;
import com.conovax.fitflow.shared.application.services.RoleService;
import com.conovax.fitflow.scheduling.application.dto.PurchaseRequest;
import com.conovax.fitflow.scheduling.application.dto.RosterEntryResponse;
import com.conovax.fitflow.scheduling.application.dto.ScheduleRequest;
import com.conovax.fitflow.scheduling.application.dto.ScheduleResponse;
import com.conovax.fitflow.scheduling.application.dto.SessionReservationResponse;
import com.conovax.fitflow.scheduling.application.dto.SessionResponse;
import com.conovax.fitflow.scheduling.application.dto.TrainerOptionResponse;
import com.conovax.fitflow.scheduling.domain.exceptions.SchedulingConflictException;
import com.conovax.fitflow.scheduling.domain.exceptions.SchedulingRuleViolationException;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.*;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.*;
import com.conovax.fitflow.shared.domain.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulingService {
    private static final int MATERIALIZATION_DAYS = 28;

    private static final List<String> ACTIVE_RESERVATION_STATUSES = List.of("CONFIRMED", "WAITLISTED", "OFFERED", "ATTENDED", "NO_SHOW");
    private static final List<String> NOTIFIABLE_RESERVATION_STATUSES = List.of("CONFIRMED", "OFFERED", "WAITLISTED");
    private static final List<String> QUEUED_RESERVATION_STATUSES = List.of("WAITLISTED", "OFFERED");

    private final ClassScheduleJpaRepository scheduleRepository;
    private final ClassSessionJpaRepository sessionRepository;
    private final ClassReservationJpaRepository reservationRepository;
    private final ClassPurchaseJpaRepository purchaseRepository;
    private final UserGymMembershipService membershipService;
    private final RoleService roleService;
    private final UserGymUsersService userGymUsersService;
    private final ClassSchedulingSettingsService settingsService;
    private final WaitlistOfferService waitlistOfferService;
    private final SchedulingNotificationService notificationService;

    @Transactional(readOnly = true)
    public List<ScheduleResponse> listSchedules(Long gymId) {
        List<ClassScheduleJpaEntity> schedules = scheduleRepository.findAllByGymIdOrderByWeekdayAscStartTimeAsc(gymId);
        Map<Long, UserGymContact> trainers = contacts(gymId, schedules.stream().map(ClassScheduleJpaEntity::getTrainerUserGymId).toList());
        return schedules.stream().map(s -> toResponse(s, trainers)).toList();
    }

    @Transactional
    public ScheduleResponse createSchedule(Long gymId, ScheduleRequest request) {
        validateTime(request);
        validateTrainer(gymId, request.trainerUserGymId());
        LocalDateTime now = LocalDateTime.now();
        ClassScheduleJpaEntity schedule = scheduleRepository.save(
                ClassScheduleJpaEntity.builder()
                        .gymId(gymId)
                        .name(request.name())
                        .description(request.description())
                        .weekday(request.weekday())
                        .startTime(request.startTime())
                        .endTime(request.endTime())
                        .capacity(request.capacity())
                        .trainerUserGymId(request.trainerUserGymId())
                        .status(request.status() == null ? "ACTIVE" : request.status())
                        .createdAt(now)
                        .updatedAt(now).build()
        );
        log.info("Horario creado: gymId={}, scheduleId={}, weekday={}, status={}",
                gymId, schedule.getId(), schedule.getWeekday(), schedule.getStatus());
        int created = materializeSchedule(schedule, LocalDate.now());
        log.info("Sesiones generadas al crear horario: gymId={}, scheduleId={}, sesiones={}", gymId, schedule.getId(), created);
        return toResponse(schedule, contacts(gymId, List.of(request.trainerUserGymId())));
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long gymId, Long scheduleId, ScheduleRequest request) {
        validateTime(request);
        ClassScheduleJpaEntity schedule = scheduleRepository.findByIdAndGymId(scheduleId, gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado"));
        validateTrainer(gymId, request.trainerUserGymId());
        Long previousTrainer = schedule.getTrainerUserGymId();
        schedule.setName(request.name());
        schedule.setDescription(request.description());
        schedule.setWeekday(request.weekday());
        schedule.setStartTime(request.startTime());
        schedule.setEndTime(request.endTime());
        schedule.setCapacity(request.capacity());
        schedule.setTrainerUserGymId(request.trainerUserGymId());
        schedule.setStatus(request.status() == null ? schedule.getStatus() : request.status());
        schedule.setUpdatedAt(LocalDateTime.now());
        ClassScheduleJpaEntity saved = scheduleRepository.save(schedule);
        log.info("Horario actualizado: gymId={}, scheduleId={}, status={}", gymId, saved.getId(), saved.getStatus());
        if (!request.trainerUserGymId().equals(previousTrainer)) {
            propagateTrainerChange(saved, previousTrainer);
        }
        int created = materializeSchedule(saved, LocalDate.now());
        log.info("Sesiones generadas al actualizar horario: gymId={}, scheduleId={}, sesiones={}", gymId, saved.getId(), created);
        return toResponse(saved, contacts(gymId, List.of(request.trainerUserGymId())));
    }

    @Transactional
    public void deleteSchedule(Long gymId, Long scheduleId) {
        ClassScheduleJpaEntity schedule = scheduleRepository.findByIdAndGymId(scheduleId, gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado"));
        schedule.setStatus("INACTIVE");
        schedule.setUpdatedAt(LocalDateTime.now());
        scheduleRepository.save(schedule);
        log.info("Horario desactivado: gymId={}, scheduleId={}", gymId, scheduleId);
    }

    @Transactional(readOnly = true)
    public List<SessionResponse> listSessions(Long gymId, LocalDate from, LocalDate to) {
        LocalDate start = from == null ? LocalDate.now() : from;
        LocalDate end = to == null ? start.plusWeeks(4) : to;
        validateWindow(start, end);
        return toSessionResponses(gymId, sessionRepository.findAllByGymIdAndStartsAtBetweenOrderByStartsAtAsc(
                gymId, start.atStartOfDay(), end.plusDays(1).atStartOfDay().minusNanos(1)));
    }

    @Transactional(readOnly = true)
    public List<SessionResponse> listTrainerSessions(Long gymId, Long trainerUserGymId, LocalDate from, LocalDate to) {
        LocalDate start = from == null ? LocalDate.now() : from;
        LocalDate end = to == null ? start.plusWeeks(4) : to;
        validateWindow(start, end);
        return toSessionResponses(gymId, sessionRepository.findAllByGymIdAndTrainerUserGymIdAndStartsAtBetweenOrderByStartsAtAsc(
                gymId, trainerUserGymId, start.atStartOfDay(), end.plusDays(1).atStartOfDay().minusNanos(1)));
    }

    /** Inscritos CONFIRMED de la sesión, visible solo para el entrenador asignado; sin documento, teléfono ni email. */
    @Transactional(readOnly = true)
    public List<RosterEntryResponse> getRoster(Long gymId, Long sessionId, Long requesterUserGymId) {
        ClassSessionJpaEntity session = sessionRepository.findByIdAndGymId(sessionId, gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada"));
        if (session.getTrainerUserGymId() == null || !session.getTrainerUserGymId().equals(requesterUserGymId)) {
            throw new AccessDeniedException("Solo el entrenador asignado puede ver los inscritos de la sesión");
        }
        List<ClassReservationJpaEntity> reservations = reservationRepository
                .findAllByGymIdAndSessionIdAndStatusInOrderByReservedAtAsc(gymId, sessionId, List.of("CONFIRMED"));
        Map<Long, UserGymContact> names = contacts(gymId, reservations.stream().map(ClassReservationJpaEntity::getUserGymId).toList());
        return reservations.stream()
                .map(r -> new RosterEntryResponse(r.getId(), r.getUserGymId(),
                        names.containsKey(r.getUserGymId()) ? names.get(r.getUserGymId()).fullName() : null,
                        r.getStatus(), r.getAttendanceConfirmation()))
                .toList();
    }

    /** Reserva online: reservar para otro user_gym exige staff activo del gimnasio. */
    @Transactional
    public ClassReservationJpaEntity reserveOnline(Long gymId, Long sessionId, Long targetUserGymId, Long requesterUserGymId) {
        if (!Objects.equals(targetUserGymId, requesterUserGymId)) {
            validateStaffUserGym(gymId, requesterUserGymId);
        }
        return reserve(gymId, sessionId, targetUserGymId, "ONLINE");
    }

    @Transactional
    public ClassReservationJpaEntity reserve(Long gymId, Long sessionId, Long userGymId, String source) {
        ClassSessionJpaEntity session = sessionRepository.lockByIdAndGymId(sessionId, gymId)
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

        long confirmed = reservationRepository.countByGymIdAndSessionIdAndStatusIn(gymId, sessionId, WaitlistOfferService.SEAT_HOLDING_STATUSES);
        String status = confirmed < session.getCapacity() ? "CONFIRMED" : "WAITLISTED";
        Integer position = "WAITLISTED".equals(status)
                ? (int) reservationRepository.countByGymIdAndSessionIdAndStatusIn(gymId, sessionId, QUEUED_RESERVATION_STATUSES) + 1 : null;
        return reservationRepository.save(ClassReservationJpaEntity.builder()
                .gymId(gymId).sessionId(sessionId).userGymId(userGymId).status(status)
                .source(source == null ? "ONLINE" : source).waitlistPosition(position)
                .reservedAt(LocalDateTime.now()).build());
    }

    @Transactional
    public void cancelReservation(Long gymId, Long reservationId, Long requesterUserGymId) {
        LockedReservation locked = lockReservation(gymId, reservationId);
        ClassReservationJpaEntity reservation = locked.reservation();
        boolean owner = requesterUserGymId != null && requesterUserGymId.equals(reservation.getUserGymId());
        if (!owner && !roleService.isActiveStaffUserGym(gymId, requesterUserGymId)) {
            throw new ResourceNotFoundException("Reserva no encontrada");
        }
        if ("CANCELLED".equals(reservation.getStatus())) return;
        String previousStatus = reservation.getStatus();
        reservation.setStatus("CANCELLED");
        reservation.setWaitlistPosition(null);
        reservationRepository.save(reservation);
        if ("CONFIRMED".equals(previousStatus) || "OFFERED".equals(previousStatus)) {
            promoteNext(locked.session());
        }
    }

    /** El dueño confirma o declina su asistencia; declinar cancela la reserva y ofrece el cupo a la lista de espera. */
    @Transactional
    public ClassReservationJpaEntity confirmAttendance(Long gymId, Long reservationId, Long requesterUserGymId, boolean willAttend) {
        LockedReservation locked = lockReservation(gymId, reservationId);
        ClassReservationJpaEntity reservation = locked.reservation();
        ClassSessionJpaEntity session = locked.session();
        assertOwner(reservation, requesterUserGymId);
        if (!"CONFIRMED".equals(reservation.getStatus())) {
            throw new SchedulingConflictException("Solo se puede confirmar la asistencia de una reserva CONFIRMED");
        }
        LocalDateTime now = LocalDateTime.now();
        assertFutureScheduledSession(session, now);

        reservation.setAttendanceConfirmedAt(now);
        if (willAttend) {
            reservation.setAttendanceConfirmation("WILL_ATTEND");
            return reservationRepository.save(reservation);
        }
        reservation.setAttendanceConfirmation("WILL_NOT_ATTEND");
        reservation.setStatus("CANCELLED");
        reservation.setWaitlistPosition(null);
        ClassReservationJpaEntity saved = reservationRepository.save(reservation);
        waitlistOfferService.offerNextSeat(session, now);
        return saved;
    }

    @Transactional
    public ClassReservationJpaEntity acceptOffer(Long gymId, Long reservationId, Long requesterUserGymId) {
        LockedReservation locked = lockReservation(gymId, reservationId);
        ClassReservationJpaEntity reservation = locked.reservation();
        ClassSessionJpaEntity session = locked.session();
        assertOwner(reservation, requesterUserGymId);
        if (!"OFFERED".equals(reservation.getStatus())) {
            throw new SchedulingConflictException("La reserva no tiene una oferta de cupo pendiente");
        }
        LocalDateTime now = LocalDateTime.now();
        if (reservation.getOfferExpiresAt() == null || !reservation.getOfferExpiresAt().isAfter(now)) {
            throw new SchedulingConflictException("La oferta de cupo ya expiró");
        }
        assertFutureScheduledSession(session, now);
        long held = reservationRepository.countByGymIdAndSessionIdAndStatusIn(gymId, session.getId(), WaitlistOfferService.SEAT_HOLDING_STATUSES);
        if (held > session.getCapacity()) {
            throw new SchedulingConflictException("El cupo ofrecido ya no está disponible");
        }
        reservation.setStatus("CONFIRMED");
        reservation.setOfferResult("ACCEPTED");
        reservation.setOfferExpiresAt(null);
        reservation.setWaitlistPosition(null);
        reservation.setAttendanceConfirmation("PENDING");
        reservation.setAttendanceConfirmedAt(null);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public ClassReservationJpaEntity declineOffer(Long gymId, Long reservationId, Long requesterUserGymId) {
        LockedReservation locked = lockReservation(gymId, reservationId);
        ClassReservationJpaEntity reservation = locked.reservation();
        assertOwner(reservation, requesterUserGymId);
        if (!"OFFERED".equals(reservation.getStatus())) {
            throw new SchedulingConflictException("La reserva no tiene una oferta de cupo pendiente");
        }
        reservation.setStatus("WAITLISTED");
        reservation.setOfferResult("DECLINED");
        reservation.setOfferExpiresAt(null);
        ClassReservationJpaEntity saved = reservationRepository.save(reservation);
        waitlistOfferService.offerNextSeat(locked.session(), LocalDateTime.now());
        return saved;
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
        LockedReservation locked = lockReservation(gymId, reservationId);
        ClassReservationJpaEntity reservation = locked.reservation();
        ClassSessionJpaEntity session = locked.session();
        validateStaffUserGym(gymId, staffUserGymId);
        if (session.getTrainerUserGymId() == null || !session.getTrainerUserGymId().equals(staffUserGymId)) {
            throw new AccessDeniedException("Solo el entrenador asignado a la sesión puede marcar asistencia");
        }
        if ("CANCELLED".equals(session.getStatus())) {
            throw new SchedulingConflictException("La sesión está cancelada");
        }
        if (!"CONFIRMED".equals(reservation.getStatus())) {
            throw new SchedulingConflictException("Solo se puede marcar asistencia de reservas CONFIRMED");
        }
        if (!"WILL_ATTEND".equals(reservation.getAttendanceConfirmation())) {
            throw new SchedulingConflictException("El usuario no confirmó su asistencia a la clase");
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(session.getStartsAt())) {
            throw new SchedulingRuleViolationException("La asistencia solo se puede marcar desde el inicio de la clase");
        }
        int windowHours = settingsService.getSettings(gymId).attendanceMarkWindowHours();
        if (now.isAfter(session.getEndsAt().plusHours(windowHours))) {
            throw new SchedulingRuleViolationException("Se superó el plazo para marcar la asistencia de esta clase");
        }
        reservation.setStatus(attended ? "ATTENDED" : "NO_SHOW");
        reservation.setCheckedInAt(now);
        reservation.setCheckedInByUserGymId(staffUserGymId);
        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public List<SessionReservationResponse> listReservationsForSession(Long gymId, Long sessionId, Long requesterUserGymId) {
        validateStaffUserGym(gymId, requesterUserGymId);
        sessionRepository.findByIdAndGymId(sessionId, gymId).orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada"));
        List<ClassReservationJpaEntity> reservations = reservationRepository.findAllByGymIdAndSessionIdOrderByReservedAtAsc(gymId, sessionId);
        Map<Long, UserGymContact> names = contacts(gymId, reservations.stream().map(ClassReservationJpaEntity::getUserGymId).toList());
        return reservations.stream()
                .map(r -> new SessionReservationResponse(r.getId(), r.getUserGymId(),
                        names.containsKey(r.getUserGymId()) ? names.get(r.getUserGymId()).fullName() : null,
                        r.getStatus(), r.getAttendanceConfirmation(), r.getWaitlistPosition()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TrainerOptionResponse> listTrainerOptions(Long gymId) {
        return userGymUsersService.getTrainerOrAdminContacts(gymId).stream()
                .map(c -> new TrainerOptionResponse(c.userGymId(), c.fullName()))
                .toList();
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
            created += materializeSchedule(schedule, from);
        }
        log.info("Ventana de sesiones materializada: gymId={}, desde={}, sesionesCreadas={}", gymId, from, created);
        return created;
    }

    private int materializeSchedule(ClassScheduleJpaEntity schedule, LocalDate from) {
        if (!"ACTIVE".equals(schedule.getStatus())) {
            log.debug("Horario omitido por no estar ACTIVE: gymId={}, scheduleId={}, status={}",
                    schedule.getGymId(), schedule.getId(), schedule.getStatus());
            return 0;
        }
        Long gymId = schedule.getGymId();
        int created = 0;
        for (int day = 0; day < MATERIALIZATION_DAYS; day++) {
            LocalDate date = from.plusDays(day);
            if (date.getDayOfWeek().getValue() != schedule.getWeekday()) continue;
            LocalDateTime starts = LocalDateTime.of(date, schedule.getStartTime());
            if (sessionRepository.existsByGymIdAndScheduleIdAndStartsAt(gymId, schedule.getId(), starts)) continue;
            sessionRepository.save(ClassSessionJpaEntity.builder().gymId(gymId).scheduleId(schedule.getId())
                    .startsAt(starts).endsAt(LocalDateTime.of(date, schedule.getEndTime()))
                    .capacity(schedule.getCapacity()).trainerUserGymId(schedule.getTrainerUserGymId())
                    .status("SCHEDULED").createdAt(LocalDateTime.now()).build());
            created++;
        }
        log.debug("Horario materializado: gymId={}, scheduleId={}, sesionesCreadas={}", gymId, schedule.getId(), created);
        return created;
    }

    /** Promoción directa por cancelación manual; omite a quien ya tuvo una oferta (offer_count > 0). */
    private void promoteNext(ClassSessionJpaEntity session) {
        Long gymId = session.getGymId();
        if (!"SCHEDULED".equals(session.getStatus()) || !session.getStartsAt().isAfter(LocalDateTime.now())) return;
        long held = reservationRepository.countByGymIdAndSessionIdAndStatusIn(gymId, session.getId(), WaitlistOfferService.SEAT_HOLDING_STATUSES);
        if (held >= session.getCapacity()) return;
        ClassReservationJpaEntity next = reservationRepository
                .findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(gymId, session.getId(), "WAITLISTED")
                .stream().filter(r -> r.getOfferCount() == null || r.getOfferCount() == 0)
                .findFirst().orElse(null);
        if (next == null) return;
        next.setStatus("CONFIRMED");
        next.setWaitlistPosition(null);
        reservationRepository.save(next);
    }

    private record LockedReservation(ClassSessionJpaEntity session, ClassReservationJpaEntity reservation) {}

    /** Bloquea la sesión antes de leer la reserva para que el conteo de cupos y los cambios de estado se serialicen. */
    private LockedReservation lockReservation(Long gymId, Long reservationId) {
        Long sessionId = reservationRepository.findSessionIdByIdAndGymId(reservationId, gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        ClassSessionJpaEntity session = sessionRepository.lockByIdAndGymId(sessionId, gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada"));
        ClassReservationJpaEntity reservation = reservationRepository.findByIdAndGymId(reservationId, gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        return new LockedReservation(session, reservation);
    }

    private void assertOwner(ClassReservationJpaEntity reservation, Long requesterUserGymId) {
        if (requesterUserGymId == null || !requesterUserGymId.equals(reservation.getUserGymId())) {
            throw new AccessDeniedException("Solo el dueño de la reserva puede realizar esta acción");
        }
    }

    private void assertFutureScheduledSession(ClassSessionJpaEntity session, LocalDateTime now) {
        if (!"SCHEDULED".equals(session.getStatus()) || !session.getStartsAt().isAfter(now)) {
            throw new SchedulingRuleViolationException("La sesión ya inició o no está disponible");
        }
    }

    private void validateWindow(LocalDate start, LocalDate end) {
        if (end.isBefore(start) || end.isAfter(start.plusWeeks(4))) {
            throw new IllegalArgumentException("La ventana de sesiones debe ser de máximo cuatro semanas");
        }
    }

    private void validateTrainer(Long gymId, Long trainerUserGymId) {
        if (trainerUserGymId == null || !roleService.isActiveTrainerOrAdminUserGym(gymId, trainerUserGymId)) {
            throw new SchedulingRuleViolationException(
                    "El entrenador debe ser un usuario activo del gimnasio con rol Entrenador o Administrador");
        }
    }

    /** Copia el nuevo entrenador a las sesiones futuras SCHEDULED y avisa a los inscritos (efecto no bloqueante, ADR 0003). */
    private void propagateTrainerChange(ClassScheduleJpaEntity schedule, Long previousTrainer) {
        Long gymId = schedule.getGymId();
        List<ClassSessionJpaEntity> futureSessions = sessionRepository
                .findAllByGymIdAndScheduleIdAndStatusAndStartsAtAfter(gymId, schedule.getId(), "SCHEDULED", LocalDateTime.now());
        if (futureSessions.isEmpty()) return;
        futureSessions.forEach(s -> s.setTrainerUserGymId(schedule.getTrainerUserGymId()));
        sessionRepository.saveAll(futureSessions);
        log.info("Entrenador propagado: gymId={}, scheduleId={}, sesiones={}", gymId, schedule.getId(), futureSessions.size());
        if (previousTrainer == null) return;

        Map<Long, ClassSessionJpaEntity> sessionsById = futureSessions.stream()
                .collect(Collectors.toMap(ClassSessionJpaEntity::getId, s -> s));
        Map<Long, List<LocalDateTime>> sessionsByUser = new LinkedHashMap<>();
        reservationRepository.findAllByGymIdAndSessionIdInAndStatusIn(gymId, sessionsById.keySet(), NOTIFIABLE_RESERVATION_STATUSES)
                .forEach(r -> sessionsByUser.computeIfAbsent(r.getUserGymId(), k -> new ArrayList<>())
                        .add(sessionsById.get(r.getSessionId()).getStartsAt()));
        sessionsByUser.values().forEach(Collections::sort);
        Map<Long, UserGymContact> trainers = contacts(gymId, List.of(previousTrainer, schedule.getTrainerUserGymId()));
        notificationService.notifyTrainerChanged(gymId, schedule.getName(),
                trainerName(trainers, previousTrainer), trainerName(trainers, schedule.getTrainerUserGymId()), sessionsByUser);
    }

    private String trainerName(Map<Long, UserGymContact> trainers, Long userGymId) {
        UserGymContact contact = trainers.get(userGymId);
        return contact == null || contact.fullName().isBlank() ? "Por confirmar" : contact.fullName();
    }

    private Map<Long, UserGymContact> contacts(Long gymId, Collection<Long> userGymIds) {
        List<Long> ids = userGymIds.stream().filter(Objects::nonNull).distinct().toList();
        return ids.isEmpty() ? Map.of() : userGymUsersService.getContactsByUserGymIds(gymId, ids);
    }

    private ScheduleResponse toResponse(ClassScheduleJpaEntity s, Map<Long, UserGymContact> trainers) {
        UserGymContact trainer = s.getTrainerUserGymId() == null ? null : trainers.get(s.getTrainerUserGymId());
        return new ScheduleResponse(s.getId(), s.getGymId(), s.getName(), s.getDescription(), s.getWeekday(),
                s.getStartTime(), s.getEndTime(), s.getCapacity(), s.getStatus(), s.getCreatedAt(), s.getUpdatedAt(),
                s.getTrainerUserGymId(), trainer == null ? null : trainer.fullName());
    }

    private List<SessionResponse> toSessionResponses(Long gymId, List<ClassSessionJpaEntity> sessions) {
        if (sessions.isEmpty()) return List.of();
        Map<Long, UserGymContact> trainers = contacts(gymId, sessions.stream().map(ClassSessionJpaEntity::getTrainerUserGymId).toList());
        Map<Long, String> scheduleNames = scheduleRepository.findAllById(
                        sessions.stream().map(ClassSessionJpaEntity::getScheduleId).distinct().toList()).stream()
                .filter(sc -> gymId.equals(sc.getGymId()))
                .collect(Collectors.toMap(ClassScheduleJpaEntity::getId, ClassScheduleJpaEntity::getName));
        return sessions.stream().map(s -> {
            UserGymContact trainer = s.getTrainerUserGymId() == null ? null : trainers.get(s.getTrainerUserGymId());
            return new SessionResponse(s.getId(), s.getGymId(), s.getScheduleId(), scheduleNames.get(s.getScheduleId()),
                    s.getStartsAt(), s.getEndsAt(), s.getCapacity(), s.getStatus(), s.getCreatedAt(),
                    s.getTrainerUserGymId(), trainer == null ? null : trainer.fullName());
        }).toList();
    }

    private void validateTime(ScheduleRequest request) {
        if (!request.endTime().isAfter(request.startTime()))
            throw new IllegalArgumentException("La hora final debe ser posterior a la inicial");
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
