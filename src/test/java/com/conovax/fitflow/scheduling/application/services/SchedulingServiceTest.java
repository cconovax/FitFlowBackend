package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.gym.application.dto.response.UserGymContact;
import com.conovax.fitflow.gym.application.services.UserGymUsersService;
import com.conovax.fitflow.membership.application.services.UserGymMembershipService;
import com.conovax.fitflow.shared.application.services.RoleService;
import com.conovax.fitflow.scheduling.application.dto.PurchaseRequest;
import com.conovax.fitflow.scheduling.application.dto.RosterEntryResponse;
import com.conovax.fitflow.scheduling.application.dto.ScheduleRequest;
import com.conovax.fitflow.scheduling.application.dto.SchedulingSettingsResponse;
import com.conovax.fitflow.scheduling.application.dto.SessionReservationResponse;
import com.conovax.fitflow.scheduling.application.dto.TrainerOptionResponse;
import com.conovax.fitflow.scheduling.domain.exceptions.SchedulingConflictException;
import com.conovax.fitflow.scheduling.domain.exceptions.SchedulingRuleViolationException;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.*;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.*;
import com.conovax.fitflow.shared.domain.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulingServiceTest {
    @Mock ClassScheduleJpaRepository scheduleRepository;
    @Mock ClassSessionJpaRepository sessionRepository;
    @Mock ClassReservationJpaRepository reservationRepository;
    @Mock ClassPurchaseJpaRepository purchaseRepository;
    @Mock UserGymMembershipService membershipService;
    @Mock RoleService roleService;
    @Mock UserGymUsersService userGymUsersService;
    @Mock ClassSchedulingSettingsService settingsService;
    @Mock WaitlistOfferService waitlistOfferService;
    @Mock SchedulingNotificationService notificationService;
    @InjectMocks SchedulingService service;

    @Test
    void reserveWaitlistsWhenSessionCapacityIsReached() {
        ClassSessionJpaEntity session = session(10L, 1);
        when(sessionRepository.lockByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(membershipService.userGymBelongsToGym(7L, 20L)).thenReturn(true);
        when(reservationRepository.findByGymIdAndSessionIdAndUserGymIdAndStatusIn(eq(7L), eq(10L), eq(20L), anyList())).thenReturn(Optional.empty());
        when(membershipService.hasActiveMembership(7L, 20L, LocalDate.now())).thenReturn(true);
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, List.of("CONFIRMED", "OFFERED", "ATTENDED"))).thenReturn(1L);
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, List.of("WAITLISTED", "OFFERED"))).thenReturn(2L);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ClassReservationJpaEntity result = service.reserve(7L, 10L, 20L, "ONLINE");

        assertEquals("WAITLISTED", result.getStatus());
        assertEquals(3, result.getWaitlistPosition());
        assertEquals(7L, result.getGymId());
    }

    @Test
    void cancellationPromotesFirstWaitlistedReservation() {
        ClassReservationJpaEntity cancelled = reservation(11L, 10L, 20L, "CONFIRMED");
        ClassReservationJpaEntity next = reservation(12L, 10L, 21L, "WAITLISTED");
        lockReservation(cancelled, session(10L, 1));
        when(reservationRepository.findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(7L, 10L, "WAITLISTED"))
                .thenReturn(List.of(next));
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.cancelReservation(7L, 11L, 20L);

        assertEquals("CANCELLED", cancelled.getStatus());
        assertEquals("CONFIRMED", next.getStatus());
        assertNull(next.getWaitlistPosition());
    }

    @Test
    void cancellationDoesNotPromoteWaitlistedUsersWhoAlreadyHadAnOffer() {
        ClassReservationJpaEntity cancelled = reservation(11L, 10L, 20L, "CONFIRMED");
        ClassReservationJpaEntity declined = reservation(12L, 10L, 21L, "WAITLISTED");
        declined.setOfferCount(1);
        lockReservation(cancelled, session(10L, 1));
        when(reservationRepository.findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(7L, 10L, "WAITLISTED"))
                .thenReturn(List.of(declined));
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.cancelReservation(7L, 11L, 20L);

        assertEquals("WAITLISTED", declined.getStatus());
    }

    @Test
    void cancellingAWaitlistedReservationDoesNotPromoteAnyone() {
        ClassReservationJpaEntity cancelled = reservation(11L, 10L, 20L, "WAITLISTED");
        lockReservation(cancelled, session(10L, 1));
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.cancelReservation(7L, 11L, 20L);

        verify(reservationRepository, never())
                .findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(anyLong(), anyLong(), anyString());
    }

    @Test
    void crossTenantSessionCannotBeReserved() {
        when(sessionRepository.lockByIdAndGymId(10L, 8L)).thenReturn(Optional.empty());
        lenient().when(sessionRepository.findById(10L)).thenReturn(Optional.of(session(10L, 1)));

        assertThrows(ResourceNotFoundException.class, () -> service.reserve(8L, 10L, 20L, "ONLINE"));
        verifyNoInteractions(membershipService);
    }

    @Test
    void crossTenantScheduleCannotBeUpdated() {
        ScheduleRequest request = new ScheduleRequest("Yoga", "", (short) 1, java.time.LocalTime.of(9, 0),
                java.time.LocalTime.of(10, 0), 10, 5L, "ACTIVE");
        when(scheduleRepository.findByIdAndGymId(30L, 8L)).thenReturn(Optional.empty());
        lenient().when(scheduleRepository.findById(30L)).thenReturn(Optional.of(ClassScheduleJpaEntity.builder()
            .id(30L).gymId(7L).name("Gym A schedule").description("").weekday((short) 1)
            .startTime(java.time.LocalTime.of(8, 0)).endTime(java.time.LocalTime.of(9, 0))
            .capacity(10).status("ACTIVE").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build()));

        assertThrows(ResourceNotFoundException.class, () -> service.updateSchedule(8L, 30L, request));
        verify(scheduleRepository).findByIdAndGymId(30L, 8L);
        verify(scheduleRepository, never()).save(any());
    }

    @Test
    void crossTenantUserGymCannotReserveOrCreatePurchase() {
        ClassSessionJpaEntity session = session(10L, 1);
        when(sessionRepository.lockByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(sessionRepository.findByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(membershipService.userGymBelongsToGym(7L, 20L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.reserve(7L, 10L, 20L, "ONLINE"));
        verifyNoInteractions(reservationRepository, purchaseRepository);

        PurchaseRequest request = new PurchaseRequest(10L, 20L, java.math.BigDecimal.TEN, "CASH", "");
        assertThrows(ResourceNotFoundException.class, () -> service.createManualPurchase(7L, request, 88L));
        verify(purchaseRepository, never()).save(any());
    }

    @Test
    void crossTenantStaffCannotMarkAttendance() {
        ClassReservationJpaEntity reservation = reservation(40L, 10L, 20L, "CONFIRMED");
        lockReservation(reservation, trainerSession(10L, 88L));
        when(roleService.isActiveStaffUserGym(7L, 99L)).thenReturn(false);

        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> service.markAttendance(7L, 40L, 99L, true));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void sellerMustBeStaffInTheSameGym() {
        ClassSessionJpaEntity session = session(10L, 10);
        PurchaseRequest request = new PurchaseRequest(10L, 20L, java.math.BigDecimal.TEN, "CASH", "");
        when(sessionRepository.findByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(membershipService.userGymBelongsToGym(7L, 20L)).thenReturn(true);

        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(false);

        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> service.createManualPurchase(7L, request, 88L));
        verify(purchaseRepository, never()).save(any());
    }

    @Test
    void manualPurchaseAlwaysUsesAuthenticatedStaffAsSeller() {
        ClassSessionJpaEntity session = session(10L, 10);
        PurchaseRequest request = new PurchaseRequest(10L, 20L, java.math.BigDecimal.TEN, "CASH", "");
        when(sessionRepository.findByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(membershipService.userGymBelongsToGym(7L, 20L)).thenReturn(true);
        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(true);
        when(reservationRepository.findByGymIdAndSessionIdAndUserGymIdAndStatusIn(eq(7L), eq(10L), eq(20L), anyList()))
                .thenReturn(Optional.empty());
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, List.of("CONFIRMED", "OFFERED", "ATTENDED")))
                .thenReturn(0L);
        when(sessionRepository.lockByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(purchaseRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ClassPurchaseJpaEntity purchase = service.createManualPurchase(7L, request, 88L);

        assertEquals(88L, purchase.getSellerUserGymId());
        verify(roleService).isActiveStaffUserGym(7L, 88L);
        verify(roleService, never()).isActiveStaffUserGym(7L, 99L);
    }

    @Test
    void attendanceIsRecordedForTheAssignedTrainerInsideTheWindow() {
        ClassReservationJpaEntity reservation = confirmedWillAttend(40L, 20L);
        lockReservation(reservation, startedSession(10L, 88L, 30, -30));
        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(true);
        when(settingsService.getSettings(7L)).thenReturn(settings(3));
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ClassReservationJpaEntity result = service.markAttendance(7L, 40L, 88L, true);

        assertEquals("ATTENDED", result.getStatus());
        assertEquals(88L, result.getCheckedInByUserGymId());
    }

    @Test
    void attendanceCannotBeMarkedByAnotherTrainerOfTheSameGym() {
        lockReservation(confirmedWillAttend(40L, 20L), startedSession(10L, 88L, 30, -30));
        when(roleService.isActiveStaffUserGym(7L, 99L)).thenReturn(true);

        assertThrows(AccessDeniedException.class, () -> service.markAttendance(7L, 40L, 99L, true));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void attendanceRequiresTheUserToHaveConfirmedAttendance() {
        ClassReservationJpaEntity reservation = confirmedWillAttend(40L, 20L);
        reservation.setAttendanceConfirmation("PENDING");
        lockReservation(reservation, startedSession(10L, 88L, 30, -30));
        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(true);

        assertThrows(SchedulingConflictException.class, () -> service.markAttendance(7L, 40L, 88L, true));
    }

    @Test
    void attendanceRequiresAConfirmedReservation() {
        ClassReservationJpaEntity reservation = confirmedWillAttend(40L, 20L);
        reservation.setStatus("WAITLISTED");
        lockReservation(reservation, startedSession(10L, 88L, 30, -30));
        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(true);

        assertThrows(SchedulingConflictException.class, () -> service.markAttendance(7L, 40L, 88L, true));
    }

    @Test
    void attendanceCannotBeMarkedBeforeTheSessionStarts() {
        lockReservation(confirmedWillAttend(40L, 20L), trainerSession(10L, 88L));
        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(true);

        assertThrows(SchedulingRuleViolationException.class, () -> service.markAttendance(7L, 40L, 88L, true));
    }

    @Test
    void attendanceCannotBeMarkedAfterTheConfiguredWindow() {
        lockReservation(confirmedWillAttend(40L, 20L), startedSession(10L, 88L, 300, 240));
        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(true);
        when(settingsService.getSettings(7L)).thenReturn(settings(3));

        assertThrows(SchedulingRuleViolationException.class, () -> service.markAttendance(7L, 40L, 88L, true));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void attendanceCannotBeMarkedOnASessionWithoutTrainer() {
        lockReservation(confirmedWillAttend(40L, 20L), startedSession(10L, null, 30, -30));
        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(true);

        assertThrows(AccessDeniedException.class, () -> service.markAttendance(7L, 40L, 88L, true));
    }

    @Test
    void crossTenantReservationCannotBeConfirmedOrMarked() {
        when(reservationRepository.findSessionIdByIdAndGymId(40L, 8L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.confirmAttendance(8L, 40L, 20L, true));
        assertThrows(ResourceNotFoundException.class, () -> service.markAttendance(8L, 40L, 88L, true));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void onlyTheOwnerCanConfirmAttendance() {
        lockReservation(reservation(40L, 10L, 20L, "CONFIRMED"), session(10L, 5));

        assertThrows(AccessDeniedException.class, () -> service.confirmAttendance(7L, 40L, 21L, true));
        verify(reservationRepository, never()).save(any());
        verifyNoInteractions(waitlistOfferService);
    }

    @Test
    void confirmingAttendanceMarksWillAttendWithoutTouchingTheWaitlist() {
        ClassReservationJpaEntity reservation = reservation(40L, 10L, 20L, "CONFIRMED");
        lockReservation(reservation, session(10L, 5));
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ClassReservationJpaEntity result = service.confirmAttendance(7L, 40L, 20L, true);

        assertEquals("WILL_ATTEND", result.getAttendanceConfirmation());
        assertNotNull(result.getAttendanceConfirmedAt());
        assertEquals("CONFIRMED", result.getStatus());
        verifyNoInteractions(waitlistOfferService);
    }

    @Test
    void decliningAttendanceCancelsTheReservationAndOffersTheSeat() {
        ClassReservationJpaEntity reservation = reservation(40L, 10L, 20L, "CONFIRMED");
        ClassSessionJpaEntity session = session(10L, 5);
        lockReservation(reservation, session);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ClassReservationJpaEntity result = service.confirmAttendance(7L, 40L, 20L, false);

        assertEquals("CANCELLED", result.getStatus());
        assertEquals("WILL_NOT_ATTEND", result.getAttendanceConfirmation());
        verify(waitlistOfferService).offerNextSeat(eq(session), any(LocalDateTime.class));
    }

    @Test
    void attendanceCannotBeConfirmedForNonConfirmedReservationsOrStartedSessions() {
        lockReservation(reservation(40L, 10L, 20L, "WAITLISTED"), session(10L, 5));
        assertThrows(SchedulingConflictException.class, () -> service.confirmAttendance(7L, 40L, 20L, true));

        ClassReservationJpaEntity confirmed = reservation(41L, 10L, 20L, "CONFIRMED");
        lockReservation(confirmed, startedSession(10L, 88L, 30, -30));
        assertThrows(SchedulingRuleViolationException.class, () -> service.confirmAttendance(7L, 41L, 20L, true));
    }

    @Test
    void acceptingAnOfferConfirmsTheReservation() {
        ClassReservationJpaEntity offered = offered(40L, 20L, LocalDateTime.now().plusMinutes(10));
        lockReservation(offered, session(10L, 5));
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, List.of("CONFIRMED", "OFFERED", "ATTENDED")))
                .thenReturn(3L);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ClassReservationJpaEntity result = service.acceptOffer(7L, 40L, 20L);

        assertEquals("CONFIRMED", result.getStatus());
        assertEquals("ACCEPTED", result.getOfferResult());
        assertNull(result.getWaitlistPosition());
        assertEquals("PENDING", result.getAttendanceConfirmation());
    }

    @Test
    void anExpiredOfferCannotBeAccepted() {
        lockReservation(offered(40L, 20L, LocalDateTime.now().minusMinutes(1)), session(10L, 5));

        assertThrows(SchedulingConflictException.class, () -> service.acceptOffer(7L, 40L, 20L));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void anOfferCannotBeAcceptedWhenTheSeatIsNoLongerAvailable() {
        lockReservation(offered(40L, 20L, LocalDateTime.now().plusMinutes(10)), session(10L, 2));
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, List.of("CONFIRMED", "OFFERED", "ATTENDED")))
                .thenReturn(3L);

        assertThrows(SchedulingConflictException.class, () -> service.acceptOffer(7L, 40L, 20L));
    }

    @Test
    void onlyTheOwnerCanAcceptOrDeclineAnOffer() {
        lockReservation(offered(40L, 20L, LocalDateTime.now().plusMinutes(10)), session(10L, 5));

        assertThrows(AccessDeniedException.class, () -> service.acceptOffer(7L, 40L, 21L));
        assertThrows(AccessDeniedException.class, () -> service.declineOffer(7L, 40L, 21L));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void decliningAnOfferReturnsToWaitlistKeepingPositionAndOffersTheNext() {
        ClassReservationJpaEntity offered = offered(40L, 20L, LocalDateTime.now().plusMinutes(10));
        ClassSessionJpaEntity session = session(10L, 5);
        lockReservation(offered, session);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ClassReservationJpaEntity result = service.declineOffer(7L, 40L, 20L);

        assertEquals("WAITLISTED", result.getStatus());
        assertEquals("DECLINED", result.getOfferResult());
        assertEquals(2, result.getWaitlistPosition());
        verify(waitlistOfferService).offerNextSeat(eq(session), any(LocalDateTime.class));
    }

    @Test
    void trainerMustBeAnActiveTrainerOrAdminOfTheSameGym() {
        ScheduleRequest request = new ScheduleRequest("Yoga", "", (short) 1, java.time.LocalTime.of(9, 0),
                java.time.LocalTime.of(10, 0), 10, 5L, "ACTIVE");
        when(roleService.isActiveTrainerOrAdminUserGym(7L, 5L)).thenReturn(false);

        assertThrows(SchedulingRuleViolationException.class, () -> service.createSchedule(7L, request));
        verify(scheduleRepository, never()).save(any());
    }

    @Test
    void createdScheduleCopiesTheTrainerToItsSessions() {
        ScheduleRequest request = new ScheduleRequest("Yoga", "", (short) LocalDate.now().getDayOfWeek().getValue(),
                java.time.LocalTime.of(9, 0), java.time.LocalTime.of(10, 0), 10, 5L, "ACTIVE");
        when(roleService.isActiveTrainerOrAdminUserGym(7L, 5L)).thenReturn(true);
        when(scheduleRepository.save(any())).thenAnswer(invocation -> {
            ClassScheduleJpaEntity s = invocation.getArgument(0);
            s.setId(30L);
            return s;
        });
        when(sessionRepository.existsByGymIdAndScheduleIdAndStartsAt(eq(7L), eq(30L), any())).thenReturn(false);
        when(sessionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.createSchedule(7L, request);

        org.mockito.ArgumentCaptor<ClassSessionJpaEntity> captor = org.mockito.ArgumentCaptor.forClass(ClassSessionJpaEntity.class);
        verify(sessionRepository, atLeastOnce()).save(captor.capture());
        captor.getAllValues().forEach(s -> assertEquals(5L, s.getTrainerUserGymId()));
    }

    @Test
    void changingTheTrainerPropagatesToFutureSessionsAndNotifiesEnrolledUsers() {
        ClassScheduleJpaEntity schedule = schedule(30L, 4L);
        ScheduleRequest request = new ScheduleRequest("Yoga", "", (short) 1, java.time.LocalTime.of(9, 0),
                java.time.LocalTime.of(10, 0), 10, 5L, "INACTIVE");
        ClassSessionJpaEntity future = session(10L, 5);
        when(scheduleRepository.findByIdAndGymId(30L, 7L)).thenReturn(Optional.of(schedule));
        when(roleService.isActiveTrainerOrAdminUserGym(7L, 5L)).thenReturn(true);
        when(scheduleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(sessionRepository.findAllByGymIdAndScheduleIdAndStatusAndStartsAtAfter(eq(7L), eq(30L), eq("SCHEDULED"), any()))
                .thenReturn(List.of(future));
        when(reservationRepository.findAllByGymIdAndSessionIdInAndStatusIn(eq(7L), anyCollection(), anyCollection()))
                .thenReturn(List.of(reservation(40L, 10L, 20L, "CONFIRMED"), reservation(41L, 10L, 21L, "WAITLISTED")));

        service.updateSchedule(7L, 30L, request);

        assertEquals(5L, future.getTrainerUserGymId());
        verify(sessionRepository).saveAll(List.of(future));
        verify(notificationService).notifyTrainerChanged(eq(7L), eq("Yoga"), anyString(), anyString(),
                argThat(users -> users.keySet().equals(java.util.Set.of(20L, 21L))));
    }

    @Test
    void keepingTheSameTrainerDoesNotPropagateOrNotify() {
        ClassScheduleJpaEntity schedule = schedule(30L, 5L);
        ScheduleRequest request = new ScheduleRequest("Yoga", "", (short) 1, java.time.LocalTime.of(9, 0),
                java.time.LocalTime.of(10, 0), 10, 5L, "INACTIVE");
        when(scheduleRepository.findByIdAndGymId(30L, 7L)).thenReturn(Optional.of(schedule));
        when(roleService.isActiveTrainerOrAdminUserGym(7L, 5L)).thenReturn(true);
        when(scheduleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.updateSchedule(7L, 30L, request);

        verifyNoInteractions(notificationService);
        verify(sessionRepository, never()).saveAll(any());
    }

    @Test
    void rosterIsOnlyVisibleToTheAssignedTrainer() {
        ClassSessionJpaEntity session = trainerSession(10L, 88L);
        when(sessionRepository.findByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));

        assertThrows(AccessDeniedException.class, () -> service.getRoster(7L, 10L, 99L));
        verifyNoInteractions(reservationRepository);
    }

    @Test
    void rosterOfAnotherGymSessionIsNotFound() {
        when(sessionRepository.findByIdAndGymId(10L, 8L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getRoster(8L, 10L, 88L));
    }

    @Test
    void rosterListsOnlyConfirmedReservationsWithNamesAndNoContactData() {
        when(sessionRepository.findByIdAndGymId(10L, 7L)).thenReturn(Optional.of(trainerSession(10L, 88L)));
        when(reservationRepository.findAllByGymIdAndSessionIdAndStatusInOrderByReservedAtAsc(7L, 10L, List.of("CONFIRMED")))
                .thenReturn(List.of(reservation(40L, 10L, 20L, "CONFIRMED")));
        when(userGymUsersService.getContactsByUserGymIds(eq(7L), anyCollection())).thenReturn(
                java.util.Map.of(20L, new UserGymContact(20L, "Ana Perez", "ana@mail.com")));

        List<RosterEntryResponse> roster = service.getRoster(7L, 10L, 88L);

        assertEquals(1, roster.size());
        assertEquals("Ana Perez", roster.get(0).fullName());
        assertEquals("PENDING", roster.get(0).attendanceConfirmation());
        assertEquals(40L, roster.get(0).reservationId());
    }

    @Test
    void trainerSessionsAreFilteredByGymAndTrainer() {
        when(sessionRepository.findAllByGymIdAndTrainerUserGymIdAndStartsAtBetweenOrderByStartsAtAsc(eq(7L), eq(88L), any(), any()))
                .thenReturn(List.of());

        assertTrue(service.listTrainerSessions(7L, 88L, null, null).isEmpty());
        verify(sessionRepository, never()).findAllByGymIdAndStartsAtBetweenOrderByStartsAtAsc(anyLong(), any(), any());
    }

    @Test
    void nonOwnerNonStaffCancellationIsReportedAsNotFoundAndChangesNothing() {
        ClassReservationJpaEntity reservation = reservation(11L, 10L, 20L, "CONFIRMED");
        lockReservation(reservation, session(10L, 1));
        when(roleService.isActiveStaffUserGym(7L, 21L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.cancelReservation(7L, 11L, 21L));

        assertEquals("CONFIRMED", reservation.getStatus());
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void activeStaffCanCancelAnotherUsersReservation() {
        ClassReservationJpaEntity reservation = reservation(11L, 10L, 20L, "WAITLISTED");
        lockReservation(reservation, session(10L, 1));
        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(true);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.cancelReservation(7L, 11L, 88L);

        assertEquals("CANCELLED", reservation.getStatus());
    }

    @Test
    void crossTenantReservationCannotBeCancelled() {
        when(reservationRepository.findSessionIdByIdAndGymId(11L, 8L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.cancelReservation(8L, 11L, 20L));
        verify(reservationRepository, never()).save(any());
        verifyNoInteractions(roleService);
    }

    @Test
    void reservingForAnotherUserGymRequiresActiveStaff() {
        when(roleService.isActiveStaffUserGym(7L, 21L)).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> service.reserveOnline(7L, 10L, 20L, 21L));
        verifyNoInteractions(sessionRepository, reservationRepository, membershipService);
    }

    @Test
    void reservingForSelfDoesNotRequireStaff() {
        ClassSessionJpaEntity session = session(10L, 5);
        when(sessionRepository.lockByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(membershipService.userGymBelongsToGym(7L, 20L)).thenReturn(true);
        when(reservationRepository.findByGymIdAndSessionIdAndUserGymIdAndStatusIn(eq(7L), eq(10L), eq(20L), anyList())).thenReturn(Optional.empty());
        when(membershipService.hasActiveMembership(7L, 20L, LocalDate.now())).thenReturn(true);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ClassReservationJpaEntity result = service.reserveOnline(7L, 10L, 20L, 20L);

        assertEquals(20L, result.getUserGymId());
        verifyNoInteractions(roleService);
    }

    @Test
    void activeStaffCanReserveForAnotherUserGymOfTheSameGym() {
        ClassSessionJpaEntity session = session(10L, 5);
        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(true);
        when(sessionRepository.lockByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(membershipService.userGymBelongsToGym(7L, 20L)).thenReturn(true);
        when(reservationRepository.findByGymIdAndSessionIdAndUserGymIdAndStatusIn(eq(7L), eq(10L), eq(20L), anyList())).thenReturn(Optional.empty());
        when(membershipService.hasActiveMembership(7L, 20L, LocalDate.now())).thenReturn(true);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        assertEquals(20L, service.reserveOnline(7L, 10L, 20L, 88L).getUserGymId());
    }

    @Test
    void listReservationsForSessionRejectsCrossTenantSession() {
        when(roleService.isActiveStaffUserGym(8L, 88L)).thenReturn(true);
        when(sessionRepository.findByIdAndGymId(10L, 8L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.listReservationsForSession(8L, 10L, 88L));
        verifyNoInteractions(reservationRepository);
    }

    @Test
    void listReservationsForSessionRejectsNonStaff() {
        when(roleService.isActiveStaffUserGym(7L, 20L)).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> service.listReservationsForSession(7L, 10L, 20L));
        verifyNoInteractions(sessionRepository, reservationRepository);
    }

    @Test
    void listReservationsForSessionReturnsOnlyPublicFieldsForOwningGym() {
        ClassSessionJpaEntity session = session(10L, 5);
        ClassReservationJpaEntity reservation = reservation(40L, 10L, 20L, "WAITLISTED");
        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(true);
        when(sessionRepository.findByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(reservationRepository.findAllByGymIdAndSessionIdOrderByReservedAtAsc(7L, 10L)).thenReturn(List.of(reservation));
        when(userGymUsersService.getContactsByUserGymIds(eq(7L), anyCollection()))
                .thenReturn(java.util.Map.of(20L, new UserGymContact(20L, "Ana Perez", "ana@test.com")));

        List<SessionReservationResponse> result = service.listReservationsForSession(7L, 10L, 88L);

        assertEquals(List.of(new SessionReservationResponse(40L, 20L, "Ana Perez", "WAITLISTED", reservation.getAttendanceConfirmation(), 1)), result);
    }

    @Test
    void trainerOptionsExposeOnlyUserGymIdAndName() {
        when(userGymUsersService.getTrainerOrAdminContacts(7L))
                .thenReturn(List.of(new UserGymContact(5L, "Luis Gomez", "luis@test.com")));

        assertEquals(List.of(new TrainerOptionResponse(5L, "Luis Gomez")), service.listTrainerOptions(7L));
    }

    @Test
    void ownerCanListTheirOwnReservationsWithoutBeingStaff() {
        when(membershipService.userGymBelongsToGym(7L, 20L)).thenReturn(true);
        when(reservationRepository.findAllByGymIdAndUserGymIdOrderByReservedAtDesc(7L, 20L)).thenReturn(List.of());

        service.listReservationsForUserGym(7L, 20L, 20L);

        verifyNoInteractions(roleService);
    }

    @Test
    void nonStaffCannotListAnotherUserGymsReservations() {
        when(membershipService.userGymBelongsToGym(7L, 20L)).thenReturn(true);
        when(roleService.isActiveStaffUserGym(7L, 21L)).thenReturn(false);

        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> service.listReservationsForUserGym(7L, 20L, 21L));
        verify(reservationRepository, never()).findAllByGymIdAndUserGymIdOrderByReservedAtDesc(anyLong(), anyLong());
    }

    private void lockReservation(ClassReservationJpaEntity reservation, ClassSessionJpaEntity session) {
        when(reservationRepository.findSessionIdByIdAndGymId(reservation.getId(), 7L)).thenReturn(Optional.of(session.getId()));
        when(sessionRepository.lockByIdAndGymId(session.getId(), 7L)).thenReturn(Optional.of(session));
        when(reservationRepository.findByIdAndGymId(reservation.getId(), 7L)).thenReturn(Optional.of(reservation));
    }

    private SchedulingSettingsResponse settings(int attendanceWindowHours) {
        return new SchedulingSettingsResponse(7L, attendanceWindowHours, 1, 30);
    }

    private ClassSessionJpaEntity trainerSession(Long id, Long trainerUserGymId) {
        ClassSessionJpaEntity session = session(id, 5);
        session.setTrainerUserGymId(trainerUserGymId);
        return session;
    }

    private ClassSessionJpaEntity startedSession(Long id, Long trainerUserGymId, int startedMinutesAgo, int endedMinutesAgo) {
        ClassSessionJpaEntity session = trainerSession(id, trainerUserGymId);
        session.setStartsAt(LocalDateTime.now().minusMinutes(startedMinutesAgo));
        session.setEndsAt(LocalDateTime.now().minusMinutes(endedMinutesAgo));
        return session;
    }

    private ClassReservationJpaEntity confirmedWillAttend(Long id, Long userGymId) {
        ClassReservationJpaEntity reservation = reservation(id, 10L, userGymId, "CONFIRMED");
        reservation.setAttendanceConfirmation("WILL_ATTEND");
        return reservation;
    }

    private ClassReservationJpaEntity offered(Long id, Long userGymId, LocalDateTime expiresAt) {
        ClassReservationJpaEntity reservation = reservation(id, 10L, userGymId, "OFFERED");
        reservation.setWaitlistPosition(2);
        reservation.setOfferCount(1);
        reservation.setOfferedAt(LocalDateTime.now());
        reservation.setOfferExpiresAt(expiresAt);
        return reservation;
    }

    private ClassScheduleJpaEntity schedule(Long id, Long trainerUserGymId) {
        return ClassScheduleJpaEntity.builder().id(id).gymId(7L).name("Yoga schedule").description("").weekday((short) 1)
                .startTime(java.time.LocalTime.of(8, 0)).endTime(java.time.LocalTime.of(9, 0)).capacity(10)
                .trainerUserGymId(trainerUserGymId).status("ACTIVE")
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
    }

    private ClassSessionJpaEntity session(Long id, int capacity) {
        return ClassSessionJpaEntity.builder().id(id).gymId(7L).scheduleId(3L)
                .startsAt(LocalDateTime.now().plusDays(1)).endsAt(LocalDateTime.now().plusDays(1).plusHours(1))
                .capacity(capacity).status("SCHEDULED").createdAt(LocalDateTime.now()).build();
    }

    private ClassReservationJpaEntity reservation(Long id, Long sessionId, Long userGymId, String status) {
        return ClassReservationJpaEntity.builder().id(id).gymId(7L).sessionId(sessionId).userGymId(userGymId)
                .status(status).source("ONLINE").reservedAt(LocalDateTime.now()).waitlistPosition("WAITLISTED".equals(status) ? 1 : null).build();
    }
}
