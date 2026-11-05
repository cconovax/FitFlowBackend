package com.conovax.fitflow.scheduling.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SchedulingSettingsRequest(
        @NotNull(message = "attendanceMarkWindowHours es obligatorio")
        @Min(value = 0, message = "attendanceMarkWindowHours debe ser mayor o igual a 0") Integer attendanceMarkWindowHours,
        @NotNull(message = "waitlistOfferCutoffHours es obligatorio")
        @Min(value = 0, message = "waitlistOfferCutoffHours debe ser mayor o igual a 0") Integer waitlistOfferCutoffHours,
        @NotNull(message = "waitlistOfferExpirationMinutes es obligatorio")
        @Min(value = 1, message = "waitlistOfferExpirationMinutes debe ser mayor a 0") Integer waitlistOfferExpirationMinutes) {}
