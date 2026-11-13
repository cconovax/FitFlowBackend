package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.scheduling.application.dto.SchedulingSettingsResponse;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassReservationJpaEntity;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassScheduleJpaEntity;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassSessionJpaEntity;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.ClassReservationJpaRepository;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.ClassScheduleJpaRepository;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.ClassSessionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Ciclo de ofertas de cupo a la lista de espera. Todo método que muta debe ejecutarse con la sesión
 * bloqueada (lockByIdAndGymId) para serializar el conteo de cupos.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WaitlistOfferService {
    static final List<String> SEAT_HOLDING_STATUSES = List.of("CONFIRMED", "OFFERED", "ATTENDED");

    private final ClassSessionJpaRepository sessionRepository;
    private final ClassReservationJpaRepository reservationRepository;
    private final ClassScheduleJpaRepository scheduleRepository;
    private final ClassSchedulingSettingsService settingsService;
    private final SchedulingNotificationService notificationService;

    /** Ofrece un cupo libre al primer WAITLISTED nunca ofertado, si el cutoff y la capacidad lo permiten. */
    public boolean offerNextSeat(ClassSessionJpaEntity session, LocalDateTime now) {
        if (!"SCHEDULED".equals(session.getStatus())) return false;
        SchedulingSettingsResponse settings = settingsService.getSettings(session.getGymId());
        if (!session.getStartsAt().isAfter(now.plusHours(settings.waitlistOfferCutoffHours()))) return false;

        Long gymId = session.getGymId();
        long held = reservationRepository.countByGymIdAndSessionIdAndStatusIn(gymId, session.getId(), SEAT_HOLDING_STATUSES);
        if (held >= session.getCapacity()) return false;

        ClassReservationJpaEntity candidate = reservationRepository
                .findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(gymId, session.getId(), "WAITLISTED")
                .stream()
                .filter(r -> r.getOfferCount() == null || r.getOfferCount() == 0)
                .findFirst().orElse(null);
        if (candidate == null) return false;

        LocalDateTime expiresAt = now.plusMinutes(settings.waitlistOfferExpirationMinutes());
        candidate.setStatus("OFFERED");
        candidate.setOfferCount((candidate.getOfferCount() == null ? 0 : candidate.getOfferCount()) + 1);
        candidate.setOfferedAt(now);
        candidate.setOfferExpiresAt(expiresAt);
        candidate.setOfferResult(null);
        reservationRepository.save(candidate);

        String className = scheduleRepository.findByIdAndGymId(session.getScheduleId(), gymId)
                .map(ClassScheduleJpaEntity::getName).orElse("Clase");
        notificationService.notifyWaitlistOffer(gymId, candidate.getUserGymId(), className, session.getStartsAt(),
                expiresAt, settings.waitlistOfferExpirationMinutes());
        log.info("Cupo ofrecido: gymId={}, sessionId={}, reservationId={}, expiraEn={}", gymId, session.getId(),
                candidate.getId(), expiresAt);
        return true;
    }

    /** Vence las ofertas caducadas de una sesión y ofrece los cupos liberados al siguiente elegible. */
    @Transactional
    public int expireOverdueOffers(Long gymId, Long sessionId) {
        ClassSessionJpaEntity session = sessionRepository.lockByIdAndGymId(sessionId, gymId).orElse(null);
        if (session == null) return 0;
        LocalDateTime now = LocalDateTime.now();
        int expired = 0;
        for (ClassReservationJpaEntity offered : reservationRepository
                .findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(gymId, sessionId, "OFFERED")) {
            if (offered.getOfferExpiresAt() == null || offered.getOfferExpiresAt().isAfter(now)) continue;
            offered.setStatus("WAITLISTED");
            offered.setOfferResult("EXPIRED");
            offered.setOfferExpiresAt(null);
            reservationRepository.save(offered);
            expired++;
        }
        if (expired > 0) {
            while (offerNextSeat(session, now)) {
                // cada iteración marca un candidato con offer_count > 0, por lo que el bucle termina
            }
        }
        return expired;
    }
}
