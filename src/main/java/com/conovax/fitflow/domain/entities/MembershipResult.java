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
public class MembershipResult {
	private Long id;
	private Long userGymMembershipId;
	private Long coachId;
	private BigDecimal startWeight;
	private BigDecimal endWeight;
	private BigDecimal startFat;
	private BigDecimal endFat;
	private BigDecimal startMuscleMass;
	private BigDecimal endMuscleMass;
	private LocalDate createdAt;
	private Boolean status;
}
