package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información de la moneda")
public record CurrencyResponse(
		@Schema(description = "ID", example = "1")
		Long id,
		@Schema(description = "Nombre", example = "Peso Colombiano")
		String name,
		@Schema(description = "Codigo", example = "COP")
		String code,
		@Schema(description = "Estado", example = "true")
		Boolean status
) {
}