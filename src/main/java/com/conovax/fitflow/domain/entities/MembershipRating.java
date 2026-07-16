package com.conovax.sexbody.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MembershipRating {
	private Long id;
	private Long userGymMembershipId;
	private Long coachId;
	private LocalDate date;
	private BigDecimal weight;
	private String observation;
	private BigDecimal porcentageFat;
	private BigDecimal muscleMass;
	private Boolean status;
}
