package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.scheduling.application.dto.SchedulingSettingsRequest;
import com.conovax.fitflow.scheduling.application.dto.SchedulingSettingsResponse;
import com.conovax.fitflow.scheduling.domain.exceptions.SchedulingRuleViolationException;
import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassSchedulingSettingsJpaEntity;
import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.ClassSchedulingSettingsJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassSchedulingSettingsServiceTest {
    @Mock ClassSchedulingSettingsJpaRepository repository;
    @InjectMocks ClassSchedulingSettingsService service;

    @Test
    void returnsDefaultsWhenTheGymHasNoRow() {
        when(repository.findById(7L)).thenReturn(Optional.empty());

        SchedulingSettingsResponse result = service.getSettings(7L);

        assertEquals(new SchedulingSettingsResponse(7L, 3, 1, 30), result);
    }

    @Test
    void readsOnlyTheRequestedGymRow() {
        when(repository.findById(8L)).thenReturn(Optional.of(row(8L, 5, 2, 15)));

        assertEquals(5, service.getSettings(8L).attendanceMarkWindowHours());
        verify(repository, never()).findById(7L);
    }

    @Test
    void createsTheRowWhenMissingAndUpdatesOnlyTheGivenGym() {
        when(repository.findById(7L)).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        SchedulingSettingsResponse result = service.updateSettings(7L, new SchedulingSettingsRequest(4, 0, 45));

        assertEquals(new SchedulingSettingsResponse(7L, 4, 0, 45), result);
        verify(repository).save(argThat(e -> e.getGymId().equals(7L)));
    }

    @Test
    void rejectsOutOfRangeValues() {
        assertThrows(SchedulingRuleViolationException.class, () -> service.updateSettings(7L, new SchedulingSettingsRequest(-1, 1, 30)));
        assertThrows(SchedulingRuleViolationException.class, () -> service.updateSettings(7L, new SchedulingSettingsRequest(3, -1, 30)));
        assertThrows(SchedulingRuleViolationException.class, () -> service.updateSettings(7L, new SchedulingSettingsRequest(3, 1, 0)));
        verify(repository, never()).save(any());
    }

    private ClassSchedulingSettingsJpaEntity row(Long gymId, int attendance, int cutoff, int expiration) {
        return ClassSchedulingSettingsJpaEntity.builder().gymId(gymId).attendanceMarkWindowHours(attendance)
                .waitlistOfferCutoffHours(cutoff).waitlistOfferExpirationMinutes(expiration)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
    }
}
