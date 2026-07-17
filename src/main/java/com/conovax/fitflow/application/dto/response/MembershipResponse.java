package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Respuesta de membresía")
public record MembershipResponse(
		Long id,
		String name,
		String description,
		Integer durationDay,
		Boolean status,
		Long gymId,
		@Schema(description = "IDs de los beneficios asociados a la membresía")
		List<Long> benefitIds
) {
}
