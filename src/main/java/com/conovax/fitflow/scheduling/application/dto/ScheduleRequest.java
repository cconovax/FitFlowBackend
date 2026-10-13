package com.conovax.fitflow.scheduling.application.dto;

import jakarta.validation.constraints.*;
import java.time.LocalTime;

public record ScheduleRequest(
        @NotBlank @Size(max = 120) String name,
        @Size(max = 500) String description,
        @NotNull @Min(1) @Max(7) Short weekday,
        @NotNull LocalTime startTime,
        @NotNull LocalTime endTime,
        @NotNull @Positive Integer capacity,
        @Pattern(regexp = "ACTIVE|INACTIVE") String status) {}
