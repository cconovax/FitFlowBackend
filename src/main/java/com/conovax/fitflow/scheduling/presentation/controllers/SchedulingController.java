package com.conovax.fitflow.scheduling.presentation.controllers;

import com.conovax.fitflow.scheduling.application.dto.*;
import com.conovax.fitflow.scheduling.application.services.ClassSchedulingSettingsService;
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
    private final ClassSchedulingSettingsService settingsService;

    @GetMapping("/schedules")
    @RequirePermission("get:all:class:schedule")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public List<ScheduleResponse> schedules(@PathVariable Long gymId) {
        assertCurrentGym(gymId);
        return service.listSchedules(gymId);
    }

    @PostMapping("/schedules")
    @RequirePermission("create:class:schedule")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public ResponseEntity<ScheduleResponse> createSchedule(
            @PathVariable Long gymId,
            @Valid @RequestBody ScheduleRequest request
    ) {
        assertCurrentGym(gymId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createSchedule(gymId, request));
    }

    @PutMapping("/schedules/{scheduleId}")
    @RequirePermission("update:class:schedule")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public ScheduleResponse updateSchedule(@PathVariable Long gymId, @PathVariable Long scheduleId, @Valid @RequestBody ScheduleRequest request) {
        assertCurrentGym(gymId);
        return service.updateSchedule(gymId, scheduleId, request);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    @RequirePermission("delete:class:schedule")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long gymId, @PathVariable Long scheduleId) {
        assertCurrentGym(gymId);
        service.deleteSchedule(gymId, scheduleId); return ResponseEntity.noContent().build();
    }

    @GetMapping("/schedules/trainer-sessions")
    @RequirePermission("get:all:trainer:class:session")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public List<SessionResponse> trainerSessions(
            @PathVariable Long gymId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        assertCurrentGym(gymId);
        return service.listTrainerSessions(gymId, currentUserGymId(), from, to);
    }

    @GetMapping("/sessions")
    @RequirePermission("get:all:class:session")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public List<SessionResponse> sessions(
            @PathVariable Long gymId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        assertCurrentGym(gymId);
        return service.listSessions(gymId, from, to);
    }

    @PostMapping("/reservations")
    @RequirePermission("create:class:reservation")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public ResponseEntity<ClassReservationJpaEntity> reserve(@PathVariable Long gymId, @Valid @RequestBody ReservationRequest request) {
        assertCurrentGym(gymId);
        Long requesterUserGymId = currentUserGymId();
        Long userGymId = request.userGymId() == null ? requesterUserGymId : request.userGymId();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.reserveOnline(gymId, request.sessionId(), userGymId, requesterUserGymId));
    }

    @GetMapping("/sessions/{sessionId}/reservations")
    @RequirePermission("get:all:class:session:reservation")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public List<SessionReservationResponse> sessionReservations(@PathVariable Long gymId, @PathVariable Long sessionId) {
        assertCurrentGym(gymId);
        return service.listReservationsForSession(gymId, sessionId, currentUserGymId());
    }

    @GetMapping("/trainers")
    @RequirePermission("get:all:class:schedule")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public List<TrainerOptionResponse> trainers(@PathVariable Long gymId) {
        assertCurrentGym(gymId);
        return service.listTrainerOptions(gymId);
    }

    @GetMapping("/reservations")
    @RequirePermission("get:all:user:class:reservation")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public List<ClassReservationJpaEntity> myReservations(@PathVariable Long gymId, @RequestParam(required = false) Long userGymId) {
        assertCurrentGym(gymId);
        Long requesterUserGymId = currentUserGymId();
        Long target = userGymId == null ? requesterUserGymId : userGymId;
        return service.listReservationsForUserGym(gymId, target, requesterUserGymId);
    }

    @DeleteMapping("/reservations/{reservationId}")
    @RequirePermission("cancel:class:reservation")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public ResponseEntity<Void> cancel(@PathVariable Long gymId, @PathVariable Long reservationId) {
        assertCurrentGym(gymId);
        service.cancelReservation(gymId, reservationId, currentUserGymId()); return ResponseEntity.noContent().build();
    }

    @PostMapping("/purchases")
    @RequirePermission("create:class:purchase")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public ResponseEntity<ClassPurchaseJpaEntity> purchase(@PathVariable Long gymId, @Valid @RequestBody PurchaseRequest request) {
        assertCurrentGym(gymId);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createManualPurchase(gymId, request, currentUserGymId()));
    }

    @GetMapping("/sessions/{sessionId}/roster")
    @RequirePermission("get:all:trainer:class:session")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public List<RosterEntryResponse> roster(@PathVariable Long gymId, @PathVariable Long sessionId) {
        assertCurrentGym(gymId);
        return service.getRoster(gymId, sessionId, currentUserGymId());
    }

    @PostMapping("/reservations/{reservationId}/attendance-confirmation")
    @RequirePermission("confirm:class:attendance")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public ClassReservationJpaEntity confirmAttendance(
            @PathVariable Long gymId,
            @PathVariable Long reservationId,
            @Valid @RequestBody AttendanceConfirmationRequest request
    ) {
        assertCurrentGym(gymId);
        return service.confirmAttendance(gymId, reservationId, currentUserGymId(), request.willAttend());
    }

    @PostMapping("/reservations/{reservationId}/offer/accept")
    @RequirePermission("confirm:class:attendance")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public ClassReservationJpaEntity acceptOffer(@PathVariable Long gymId, @PathVariable Long reservationId) {
        assertCurrentGym(gymId);
        return service.acceptOffer(gymId, reservationId, currentUserGymId());
    }

    @PostMapping("/reservations/{reservationId}/offer/decline")
    @RequirePermission("confirm:class:attendance")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public ClassReservationJpaEntity declineOffer(@PathVariable Long gymId, @PathVariable Long reservationId) {
        assertCurrentGym(gymId);
        return service.declineOffer(gymId, reservationId, currentUserGymId());
    }

    @GetMapping("/settings")
    @RequirePermission("get:class:scheduling:settings")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public SchedulingSettingsResponse getSettings(@PathVariable Long gymId) {
        assertCurrentGym(gymId);
        return settingsService.getSettings(gymId);
    }

    @PutMapping("/settings")
    @RequirePermission("update:class:scheduling:settings")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
    public SchedulingSettingsResponse updateSettings(@PathVariable Long gymId, @Valid @RequestBody SchedulingSettingsRequest request) {
        assertCurrentGym(gymId);
        return settingsService.updateSettings(gymId, request);
    }

    @PostMapping("/attendance")
    @RequirePermission("mark:class:attendance")
    @RequireFeature("CLASS_SCHEDULING")
    @RequireActiveGymSubscription
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
