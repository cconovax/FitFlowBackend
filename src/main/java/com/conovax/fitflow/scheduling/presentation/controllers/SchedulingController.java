package com.conovax.fitflow.scheduling.presentation.controllers;

import com.conovax.fitflow.scheduling.application.dto.*;
import com.conovax.fitflow.scheduling.application.services.SchedulingService;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.*;
import com.conovax.fitflow.shared.infrastructure.security.GymAuthenticationDetails;
import com.conovax.fitflow.shared.infrastructure.security.annotations.RequireActiveGymSubscription;
import com.conovax.fitflow.shared.infrastructure.security.annotations.RequireFeature;
import com.conovax.fitflow.shared.infrastructure.security.annotations.RequirePermission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/scheduling/gyms/{gymId}")
@RequiredArgsConstructor
public class SchedulingController {
    private final SchedulingService service;

    @GetMapping("/schedules")
    @RequirePermission("get:all:class:schedule") @RequireFeature("CLASS_SCHEDULING") @RequireActiveGymSubscription
    public List<ClassScheduleJpaEntity> schedules(@PathVariable Long gymId) { assertCurrentGym(gymId); return service.listSchedules(gymId); }

    @PostMapping("/schedules")
    @RequirePermission("manage:class:schedule") @RequireFeature("CLASS_SCHEDULING") @RequireActiveGymSubscription
    public ResponseEntity<ClassScheduleJpaEntity> createSchedule(@PathVariable Long gymId, @Valid @RequestBody ScheduleRequest request) {
        assertCurrentGym(gymId);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSchedule(gymId, request));
    }

    @PutMapping("/schedules/{scheduleId}")
    @RequirePermission("manage:class:schedule") @RequireFeature("CLASS_SCHEDULING") @RequireActiveGymSubscription
    public ClassScheduleJpaEntity updateSchedule(@PathVariable Long gymId, @PathVariable Long scheduleId, @Valid @RequestBody ScheduleRequest request) {
        assertCurrentGym(gymId);
        return service.updateSchedule(gymId, scheduleId, request);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    @RequirePermission("manage:class:schedule") @RequireFeature("CLASS_SCHEDULING") @RequireActiveGymSubscription
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long gymId, @PathVariable Long scheduleId) {
        assertCurrentGym(gymId);
        service.deleteSchedule(gymId, scheduleId); return ResponseEntity.noContent().build();
    }

    @GetMapping("/sessions")
    @RequirePermission("get:all:class:schedule") @RequireFeature("CLASS_SCHEDULING") @RequireActiveGymSubscription
    public List<ClassSessionJpaEntity> sessions(@PathVariable Long gymId, @RequestParam(required = false) LocalDate from, @RequestParam(required = false) LocalDate to) {
        assertCurrentGym(gymId);
        return service.listSessions(gymId, from, to);
    }

    @PostMapping("/reservations")
    @RequirePermission("manage:class:reservation") @RequireFeature("CLASS_SCHEDULING") @RequireActiveGymSubscription
    public ResponseEntity<ClassReservationJpaEntity> reserve(@PathVariable Long gymId, @Valid @RequestBody ReservationRequest request) {
        assertCurrentGym(gymId);
        Long userGymId = request.userGymId() == null ? currentUserGymId() : request.userGymId();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.reserve(gymId, request.sessionId(), userGymId, "ONLINE"));
    }

    @GetMapping("/sessions/{sessionId}/reservations")
    @RequirePermission("get:all:class:schedule") @RequireFeature("CLASS_SCHEDULING") @RequireActiveGymSubscription
    public List<ClassReservationJpaEntity> sessionReservations(@PathVariable Long gymId, @PathVariable Long sessionId) {
        assertCurrentGym(gymId);
        return service.listReservationsForSession(gymId, sessionId);
    }

    @GetMapping("/reservations")
    @RequirePermission("manage:class:reservation") @RequireFeature("CLASS_SCHEDULING") @RequireActiveGymSubscription
    public List<ClassReservationJpaEntity> myReservations(@PathVariable Long gymId, @RequestParam(required = false) Long userGymId) {
        assertCurrentGym(gymId);
        Long requesterUserGymId = currentUserGymId();
        Long target = userGymId == null ? requesterUserGymId : userGymId;
        return service.listReservationsForUserGym(gymId, target, requesterUserGymId);
    }

    @DeleteMapping("/reservations/{reservationId}")
    @RequirePermission("manage:class:reservation") @RequireFeature("CLASS_SCHEDULING") @RequireActiveGymSubscription
    public ResponseEntity<Void> cancel(@PathVariable Long gymId, @PathVariable Long reservationId) {
        assertCurrentGym(gymId);
        service.cancelReservation(gymId, reservationId); return ResponseEntity.noContent().build();
    }

    @PostMapping("/purchases")
    @RequirePermission("create:class:purchase") @RequireFeature("CLASS_SCHEDULING") @RequireActiveGymSubscription
    public ResponseEntity<ClassPurchaseJpaEntity> purchase(@PathVariable Long gymId, @Valid @RequestBody PurchaseRequest request) {
        assertCurrentGym(gymId);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createManualPurchase(gymId, request, currentUserGymId()));
    }

    @PostMapping("/attendance")
    @RequirePermission("manage:class:attendance") @RequireFeature("CLASS_SCHEDULING") @RequireActiveGymSubscription
    public ClassReservationJpaEntity attendance(@PathVariable Long gymId, @Valid @RequestBody AttendanceRequest request) {
        assertCurrentGym(gymId);
        return service.markAttendance(gymId, request.reservationId(), currentUserGymId(), request.attended());
    }

    private Long currentUserGymId() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (details instanceof GymAuthenticationDetails gymDetails && gymDetails.userGymId() != null) return gymDetails.userGymId();
        throw new IllegalArgumentException("La sesión no tiene un userGymId seleccionado");
    }

    private void assertCurrentGym(Long gymId) {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (details instanceof GymAuthenticationDetails gymDetails && gymId.equals(gymDetails.gymId())) return;
        throw new org.springframework.security.access.AccessDeniedException("El recurso no pertenece al gimnasio seleccionado");
    }
}
