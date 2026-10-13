package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.membership.application.services.UserGymMembershipService;
import com.conovax.fitflow.shared.application.services.RoleService;
import com.conovax.fitflow.scheduling.application.dto.PurchaseRequest;
import com.conovax.fitflow.scheduling.application.dto.ScheduleRequest;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.*;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.*;
import com.conovax.fitflow.shared.domain.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @InjectMocks SchedulingService service;

    @Test
    void reserveWaitlistsWhenSessionCapacityIsReached() {
        ClassSessionJpaEntity session = session(10L, 1);
        when(sessionRepository.findByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(membershipService.userGymBelongsToGym(7L, 20L)).thenReturn(true);
        when(reservationRepository.findByGymIdAndSessionIdAndUserGymIdAndStatusIn(eq(7L), eq(10L), eq(20L), anyList())).thenReturn(Optional.empty());
        when(membershipService.hasActiveMembership(7L, 20L, LocalDate.now())).thenReturn(true);
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, List.of("CONFIRMED", "ATTENDED"))).thenReturn(1L);
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, List.of("WAITLISTED"))).thenReturn(2L);
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
        when(reservationRepository.findByIdAndGymId(11L, 7L)).thenReturn(Optional.of(cancelled));
        when(reservationRepository.findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(7L, 10L, "WAITLISTED"))
                .thenReturn(List.of(next));
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.cancelReservation(7L, 11L);

        assertEquals("CANCELLED", cancelled.getStatus());
        assertEquals("CONFIRMED", next.getStatus());
        assertNull(next.getWaitlistPosition());
    }

    @Test
    void crossTenantSessionCannotBeReserved() {
        when(sessionRepository.findByIdAndGymId(10L, 8L)).thenReturn(Optional.empty());
        lenient().when(sessionRepository.findById(10L)).thenReturn(Optional.of(session(10L, 1)));

        assertThrows(ResourceNotFoundException.class, () -> service.reserve(8L, 10L, 20L, "ONLINE"));
        verifyNoInteractions(membershipService);
    }

    @Test
    void crossTenantScheduleCannotBeUpdated() {
        ScheduleRequest request = new ScheduleRequest("Yoga", "", (short) 1, java.time.LocalTime.of(9, 0),
                java.time.LocalTime.of(10, 0), 10, "ACTIVE");
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
        when(reservationRepository.findByIdAndGymId(40L, 7L)).thenReturn(Optional.of(reservation));
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
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, List.of("CONFIRMED", "ATTENDED")))
                .thenReturn(0L);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(purchaseRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ClassPurchaseJpaEntity purchase = service.createManualPurchase(7L, request, 88L);

        assertEquals(88L, purchase.getSellerUserGymId());
        verify(roleService).isActiveStaffUserGym(7L, 88L);
        verify(roleService, never()).isActiveStaffUserGym(7L, 99L);
    }

    @Test
    void attendanceIsRecordedForStaffFromTheSameGym() {
        ClassReservationJpaEntity reservation = reservation(40L, 10L, 20L, "CONFIRMED");
        when(reservationRepository.findByIdAndGymId(40L, 7L)).thenReturn(Optional.of(reservation));
        when(roleService.isActiveStaffUserGym(7L, 88L)).thenReturn(true);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ClassReservationJpaEntity result = service.markAttendance(7L, 40L, 88L, true);

        assertEquals("ATTENDED", result.getStatus());
        assertEquals(88L, result.getCheckedInByUserGymId());
    }

    @Test
    void listReservationsForSessionRejectsCrossTenantSession() {
        when(sessionRepository.findByIdAndGymId(10L, 8L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.listReservationsForSession(8L, 10L));
        verifyNoInteractions(reservationRepository);
    }

    @Test
    void listReservationsForSessionReturnsRosterForOwningGym() {
        ClassSessionJpaEntity session = session(10L, 5);
        ClassReservationJpaEntity reservation = reservation(40L, 10L, 20L, "CONFIRMED");
        when(sessionRepository.findByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(reservationRepository.findAllByGymIdAndSessionIdOrderByReservedAtAsc(7L, 10L)).thenReturn(List.of(reservation));

        List<ClassReservationJpaEntity> result = service.listReservationsForSession(7L, 10L);

        assertEquals(List.of(reservation), result);
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
