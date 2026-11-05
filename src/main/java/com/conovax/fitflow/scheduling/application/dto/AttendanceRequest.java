package com.conovax.fitflow.scheduling.application.dto;

import jakarta.validation.constraints.NotNull;

public record AttendanceRequest(@NotNull Long reservationId, @NotNull Boolean attended) {}
