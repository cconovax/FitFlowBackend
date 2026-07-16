package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información del país")
public record CountryResponse(
		@Schema(description = "ID", example = "1")
		Long id,
		@Schema(description = "Nombre", example = "Colombia")
		String name,
		@Schema(description = "Estado", example = "true")
		Boolean status
) {
}
