package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassReservationJpaEntity;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.ClassReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Vence ofertas de cupo caducadas; no es el recordatorio previo a la clase. */
@Slf4j
@Component
@RequiredArgsConstructor
public class WaitlistOfferExpirationJob {
    private final ClassReservationJpaRepository reservationRepository;
    private final WaitlistOfferService waitlistOfferService;

    @Scheduled(fixedDelay = 60_000)
    public void expireOverdueOffers() {
        List<ClassReservationJpaEntity> overdue = reservationRepository
                .findAllByStatusAndOfferExpiresAtLessThanEqual("OFFERED", LocalDateTime.now());
        Set<List<Long>> sessions = new LinkedHashSet<>();
        overdue.forEach(r -> sessions.add(List.of(r.getGymId(), r.getSessionId())));
        for (List<Long> key : sessions) {
            try {
                waitlistOfferService.expireOverdueOffers(key.get(0), key.get(1));
            } catch (RuntimeException e) {
                log.error("Falló el vencimiento de ofertas: gymId={}, sessionId={}", key.get(0), key.get(1), e);
            }
        }
    }
}
