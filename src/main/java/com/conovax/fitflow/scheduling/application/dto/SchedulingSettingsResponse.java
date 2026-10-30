package com.conovax.fitflow.scheduling.application.dto;

public record SchedulingSettingsResponse(
        Long gymId, Integer attendanceMarkWindowHours, Integer waitlistOfferCutoffHours, Integer waitlistOfferExpirationMinutes) {}
