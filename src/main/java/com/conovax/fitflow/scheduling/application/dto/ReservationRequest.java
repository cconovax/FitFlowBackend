package com.conovax.fitflow.scheduling.application.dto;

import jakarta.validation.constraints.NotNull;

public record ReservationRequest(@NotNull Long sessionId, Long userGymId) {}
