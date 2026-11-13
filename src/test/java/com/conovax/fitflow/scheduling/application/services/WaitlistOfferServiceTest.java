package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.scheduling.application.dto.SchedulingSettingsResponse;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassReservationJpaEntity;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassScheduleJpaEntity;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassSessionJpaEntity;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.ClassReservationJpaRepository;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.ClassScheduleJpaRepository;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.ClassSessionJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WaitlistOfferServiceTest {
    private static final List<String> SEATS = List.of("CONFIRMED", "OFFERED", "ATTENDED");

    @Mock ClassSessionJpaRepository sessionRepository;
    @Mock ClassReservationJpaRepository reservationRepository;
    @Mock ClassScheduleJpaRepository scheduleRepository;
    @Mock ClassSchedulingSettingsService settingsService;
    @Mock SchedulingNotificationService notificationService;
    @InjectMocks WaitlistOfferService service;

    @Test
    void offersTheSeatToTheFirstNeverOfferedWaitlistedUser() {
        ClassSessionJpaEntity session = session(LocalDateTime.now().plusHours(5), 2);
        ClassReservationJpaEntity declined = waitlisted(1L, 20L, 1, 1);
        ClassReservationJpaEntity next = waitlisted(2L, 21L, 2, 0);
        ClassReservationJpaEntity later = waitlisted(3L, 22L, 3, 0);
        stubSettings(1, 30);
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, SEATS)).thenReturn(1L);
        when(reservationRepository.findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(7L, 10L, "WAITLISTED"))
                .thenReturn(List.of(declined, next, later));
        when(scheduleRepository.findByIdAndGymId(3L, 7L)).thenReturn(Optional.of(
                ClassScheduleJpaEntity.builder().id(3L).gymId(7L).name("Yoga").build()));
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        LocalDateTime now = LocalDateTime.now();

        assertTrue(service.offerNextSeat(session, now));

        assertEquals("WAITLISTED", declined.getStatus());
        assertEquals("OFFERED", next.getStatus());
        assertEquals(1, next.getOfferCount());
        assertEquals(now, next.getOfferedAt());
        assertEquals(now.plusMinutes(30), next.getOfferExpiresAt());
        assertEquals(2, next.getWaitlistPosition());
        assertEquals("WAITLISTED", later.getStatus());
        verify(notificationService).notifyWaitlistOffer(eq(7L), eq(21L), eq("Yoga"), eq(session.getStartsAt()),
                eq(now.plusMinutes(30)), eq(30));
    }

    @Test
    void neverReoffersToUsersWhoAlreadyHadAnOffer() {
        ClassSessionJpaEntity session = session(LocalDateTime.now().plusHours(5), 2);
        stubSettings(1, 30);
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, SEATS)).thenReturn(1L);
        when(reservationRepository.findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(7L, 10L, "WAITLISTED"))
                .thenReturn(List.of(waitlisted(1L, 20L, 1, 1), waitlisted(2L, 21L, 2, 2)));

        assertFalse(service.offerNextSeat(session, LocalDateTime.now()));

        verify(reservationRepository, never()).save(any());
        verifyNoInteractions(notificationService);
    }

    @Test
    void doesNotOfferWhenTheSessionStartsWithinTheCutoff() {
        ClassSessionJpaEntity session = session(LocalDateTime.now().plusMinutes(50), 2);
        stubSettings(1, 30);

        assertFalse(service.offerNextSeat(session, LocalDateTime.now()));

        verifyNoInteractions(reservationRepository, notificationService);
    }

    @Test
    void offersWhenTheSessionStartsAfterTheCutoff() {
        ClassSessionJpaEntity session = session(LocalDateTime.now().plusMinutes(70), 2);
        stubSettings(1, 30);
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, SEATS)).thenReturn(0L);
        when(reservationRepository.findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(7L, 10L, "WAITLISTED"))
                .thenReturn(List.of(waitlisted(2L, 21L, 1, 0)));
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        assertTrue(service.offerNextSeat(session, LocalDateTime.now()));
    }

    @Test
    void doesNotOfferWhenThereIsNoFreeSeat() {
        ClassSessionJpaEntity session = session(LocalDateTime.now().plusHours(5), 2);
        stubSettings(1, 30);
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, SEATS)).thenReturn(2L);

        assertFalse(service.offerNextSeat(session, LocalDateTime.now()));

        verify(reservationRepository, never()).save(any());
    }

    @Test
    void doesNotOfferOnNonScheduledSessions() {
        ClassSessionJpaEntity session = session(LocalDateTime.now().plusHours(5), 2);
        session.setStatus("CANCELLED");

        assertFalse(service.offerNextSeat(session, LocalDateTime.now()));

        verifyNoInteractions(reservationRepository, settingsService);
    }

    @Test
    void expiredOffersReturnToWaitlistAndTheSeatGoesToTheNextEligibleUser() {
        ClassSessionJpaEntity session = session(LocalDateTime.now().plusHours(5), 2);
        ClassReservationJpaEntity expired = waitlisted(1L, 20L, 1, 1);
        expired.setStatus("OFFERED");
        expired.setOfferExpiresAt(LocalDateTime.now().minusMinutes(1));
        ClassReservationJpaEntity stillValid = waitlisted(4L, 23L, 4, 1);
        stillValid.setStatus("OFFERED");
        stillValid.setOfferExpiresAt(LocalDateTime.now().plusMinutes(10));
        ClassReservationJpaEntity next = waitlisted(2L, 21L, 2, 0);
        when(sessionRepository.lockByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(reservationRepository.findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(7L, 10L, "OFFERED"))
                .thenReturn(List.of(expired, stillValid));
        when(reservationRepository.findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(7L, 10L, "WAITLISTED"))
                .thenReturn(List.of(expired, next));
        when(reservationRepository.countByGymIdAndSessionIdAndStatusIn(7L, 10L, SEATS)).thenReturn(1L, 2L);
        stubSettings(1, 30);
        when(scheduleRepository.findByIdAndGymId(3L, 7L)).thenReturn(Optional.empty());
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        int count = service.expireOverdueOffers(7L, 10L);

        assertEquals(1, count);
        assertEquals("WAITLISTED", expired.getStatus());
        assertEquals("EXPIRED", expired.getOfferResult());
        assertNull(expired.getOfferExpiresAt());
        assertEquals("OFFERED", stillValid.getStatus());
        assertEquals("OFFERED", next.getStatus());
    }

    @Test
    void expiredOffersAreNotReofferedInsideTheCutoff() {
        ClassSessionJpaEntity session = session(LocalDateTime.now().plusMinutes(20), 2);
        ClassReservationJpaEntity expired = waitlisted(1L, 20L, 1, 1);
        expired.setStatus("OFFERED");
        expired.setOfferExpiresAt(LocalDateTime.now().minusMinutes(1));
        when(sessionRepository.lockByIdAndGymId(10L, 7L)).thenReturn(Optional.of(session));
        when(reservationRepository.findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(7L, 10L, "OFFERED"))
                .thenReturn(List.of(expired));
        stubSettings(1, 30);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.expireOverdueOffers(7L, 10L);

        assertEquals("WAITLISTED", expired.getStatus());
        verifyNoInteractions(notificationService);
    }

    @Test
    void expiringOffersOfAnotherGymSessionDoesNothing() {
        when(sessionRepository.lockByIdAndGymId(10L, 8L)).thenReturn(Optional.empty());

        assertEquals(0, service.expireOverdueOffers(8L, 10L));
        verifyNoInteractions(reservationRepository);
    }

    private void stubSettings(int cutoffHours, int expirationMinutes) {
        when(settingsService.getSettings(7L)).thenReturn(new SchedulingSettingsResponse(7L, 3, cutoffHours, expirationMinutes));
    }

    private ClassSessionJpaEntity session(LocalDateTime startsAt, int capacity) {
        return ClassSessionJpaEntity.builder().id(10L).gymId(7L).scheduleId(3L).startsAt(startsAt)
                .endsAt(startsAt.plusHours(1)).capacity(capacity).status("SCHEDULED").createdAt(LocalDateTime.now()).build();
    }

    private ClassReservationJpaEntity waitlisted(Long id, Long userGymId, int position, int offerCount) {
        return ClassReservationJpaEntity.builder().id(id).gymId(7L).sessionId(10L).userGymId(userGymId)
                .status("WAITLISTED").source("ONLINE").waitlistPosition(position).offerCount(offerCount)
                .reservedAt(LocalDateTime.now()).build();
    }
}
