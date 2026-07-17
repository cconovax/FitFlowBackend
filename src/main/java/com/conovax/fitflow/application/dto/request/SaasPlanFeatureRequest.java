package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Asignación de una feature a un plan SaaS")
public record SaasPlanFeatureRequest(
		@NotNull(message = "El saasFeatureId es requerido")
		@Positive(message = "El saasFeatureId debe ser mayor que 0")
		@Schema(description = "ID de la feature SaaS", example = "1")
		Long saasFeatureId,

		@Size(max = 100, message = "El valor de texto no puede exceder 100 caracteres")
		@Schema(description = "Valor textual opcional", example = "Hasta 3 sedes")
		String valueText,

		@Schema(description = "Valor numérico opcional", example = "10")
		BigDecimal valueNumber,

		@Schema(description = "Valor booleano opcional", example = "true")
		Boolean valueBoolean
) {
}