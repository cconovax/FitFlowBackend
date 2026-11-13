package com.conovax.fitflow.scheduling.application.dto;

import java.time.LocalDateTime;

public record SessionResponse(
        Long id, Long gymId, Long scheduleId, String scheduleName,
        LocalDateTime startsAt, LocalDateTime endsAt, Integer capacity, String status, LocalDateTime createdAt,
        Long trainerUserGymId, String trainerName) {}
