package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información del sexo")
public record SexoResponse(
		@Schema(description = "ID", example = "1")
		Long id,
		@Schema(description = "Nombre", example = "Masculino")
		String name,
		@Schema(description = "Código", example = "M")
		String code,
		@Schema(description = "Estado", example = "true")
		Boolean status
) {
}
