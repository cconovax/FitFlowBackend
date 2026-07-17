package com.conovax.fitflow.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SaasPlanFeature {
	private Long id;
	private Long saasPlanId;
	private Long saasFeatureId;
	private String valueText;
	private BigDecimal valueNumber;
	private Boolean valueBoolean;
}