package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información del departamento")
public record DepartamentResponse(
		@Schema(description = "ID", example = "1")
		Long id,
		@Schema(description = "ID del país", example = "1")
		Long countryId,
		@Schema(description = "Nombre", example = "Antioquia")
		String name,
		@Schema(description = "Estado", example = "true")
		Boolean status
) {
}
