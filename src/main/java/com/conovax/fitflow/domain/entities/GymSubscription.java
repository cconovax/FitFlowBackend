package com.conovax.fitflow.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GymSubscription {
	private Long id;
	private Long gymId;
	private Long saasPlanId;
	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean status;
	private String notes;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}