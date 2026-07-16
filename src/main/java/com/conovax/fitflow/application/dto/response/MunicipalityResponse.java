package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de municipio")
public record MunicipalityResponse(
		@Schema(description = "ID del municipio", example = "1")
		Long id,

		@Schema(description = "ID del departamento", example = "1")
		Long departamentId,

		@Schema(description = "Nombre", example = "Medellín")
		String name,

		@Schema(description = "Estado", example = "true")
		Boolean status
) {
}
