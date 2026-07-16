package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Membresía con sus beneficios")
public record MembershipWithBenefitsResponse(
		Long id,
		String name,
		String description,
		Integer durationDay,
		Boolean status,
		Long gymId,
		List<BenefitResponse> benefits,
		BigDecimal price
) {
}
