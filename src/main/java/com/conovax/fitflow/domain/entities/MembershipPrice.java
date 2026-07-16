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
public class MembershipPrice {
	private Long id;
	private Long membershipId;
	private BigDecimal price;
	private LocalDate startDate;
	private LocalDate endDate;
}
