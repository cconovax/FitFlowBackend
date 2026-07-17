package com.conovax.fitflow.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionLog {
	private Long id;
	private Long userGymId;
	private LocalDate startDate;
	private LocalDate endDate;
}
