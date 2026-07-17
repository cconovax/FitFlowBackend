package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información del tipo de documento")
public record TypeDocumentResponse(
		@Schema(description = "ID", example = "1")
		Long id,
		@Schema(description = "Nombre", example = "Cédula")
		String name,
		@Schema(description = "Código", example = "CC")
		String code,
		@Schema(description = "Estado", example = "true")
		Boolean status
) {
}
