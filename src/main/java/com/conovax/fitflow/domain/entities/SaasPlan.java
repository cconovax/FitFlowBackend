package com.conovax.sexbody.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SaasPlan {
	private Long id;
	private String code;
	private String name;
	private String description;
	private BigDecimal price;
	private Long numDays;
	private String stripePriceId;
	private Boolean status;
}