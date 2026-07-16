package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de beneficio")
public record BenefitResponse(
		Long id,
		String name,
		String description,
		Boolean status,
		Long gymId
) {
}
