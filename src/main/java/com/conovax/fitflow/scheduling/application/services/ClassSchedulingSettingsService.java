package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.scheduling.application.dto.SchedulingSettingsRequest;
import com.conovax.fitflow.scheduling.application.dto.SchedulingSettingsResponse;
import com.conovax.fitflow.scheduling.domain.exceptions.SchedulingRuleViolationException;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassSchedulingSettingsJpaEntity;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.ClassSchedulingSettingsJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClassSchedulingSettingsService {
    public static final int DEFAULT_ATTENDANCE_MARK_WINDOW_HOURS = 3;
    public static final int DEFAULT_WAITLIST_OFFER_CUTOFF_HOURS = 1;
    public static final int DEFAULT_WAITLIST_OFFER_EXPIRATION_MINUTES = 30;

    private final ClassSchedulingSettingsJpaRepository repository;

    @Transactional(readOnly = true)
    public SchedulingSettingsResponse getSettings(Long gymId) {
        return repository.findById(gymId)
                .map(this::toResponse)
                .orElseGet(() -> new SchedulingSettingsResponse(gymId, DEFAULT_ATTENDANCE_MARK_WINDOW_HOURS,
                        DEFAULT_WAITLIST_OFFER_CUTOFF_HOURS, DEFAULT_WAITLIST_OFFER_EXPIRATION_MINUTES));
    }

    @Transactional
    public SchedulingSettingsResponse updateSettings(Long gymId, SchedulingSettingsRequest request) {
        if (request.attendanceMarkWindowHours() == null || request.attendanceMarkWindowHours() < 0
                || request.waitlistOfferCutoffHours() == null || request.waitlistOfferCutoffHours() < 0) {
            throw new SchedulingRuleViolationException("Las horas configuradas deben ser mayores o iguales a 0");
        }
        if (request.waitlistOfferExpirationMinutes() == null || request.waitlistOfferExpirationMinutes() <= 0) {
            throw new SchedulingRuleViolationException("La expiración de la oferta debe ser mayor a 0 minutos");
        }
        LocalDateTime now = LocalDateTime.now();
        ClassSchedulingSettingsJpaEntity entity = repository.findById(gymId)
                .orElseGet(() -> ClassSchedulingSettingsJpaEntity.builder().gymId(gymId).createdAt(now).build());
        entity.setAttendanceMarkWindowHours(request.attendanceMarkWindowHours());
        entity.setWaitlistOfferCutoffHours(request.waitlistOfferCutoffHours());
        entity.setWaitlistOfferExpirationMinutes(request.waitlistOfferExpirationMinutes());
        entity.setUpdatedAt(now);
        return toResponse(repository.save(entity));
    }

    private SchedulingSettingsResponse toResponse(ClassSchedulingSettingsJpaEntity e) {
        return new SchedulingSettingsResponse(e.getGymId(), e.getAttendanceMarkWindowHours(),
                e.getWaitlistOfferCutoffHours(), e.getWaitlistOfferExpirationMinutes());
    }
}
