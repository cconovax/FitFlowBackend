package com.conovax.fitflow.scheduling.application.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record ScheduleResponse(
        Long id, Long gymId, String name, String description, Short weekday,
        LocalTime startTime, LocalTime endTime, Integer capacity, String status,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        Long trainerUserGymId, String trainerName) {}
