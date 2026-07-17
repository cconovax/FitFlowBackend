package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Feature asignada a un plan SaaS")
public record SaasPlanFeatureResponse(
		@Schema(description = "ID de la relación plan-feature", example = "1")
		Long id,

		@Schema(description = "ID de la feature", example = "2")
		Long saasFeatureId,

		@Schema(description = "Código técnico de la feature", example = "MAX_USERS")
		String featureCode,

		@Schema(description = "Nombre de la feature", example = "Máximo de usuarios")
		String featureName,

		@Schema(description = "Valor textual opcional", example = "Hasta 3 sedes")
		String valueText,

		@Schema(description = "Valor numérico opcional", example = "10")
		BigDecimal valueNumber,

		@Schema(description = "Valor booleano opcional", example = "true")
		Boolean valueBoolean
) {
}