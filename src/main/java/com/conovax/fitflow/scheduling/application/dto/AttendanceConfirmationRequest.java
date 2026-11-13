package com.conovax.fitflow.scheduling.application.dto;

import jakarta.validation.constraints.NotNull;

public record AttendanceConfirmationRequest(@NotNull Boolean willAttend) {}
