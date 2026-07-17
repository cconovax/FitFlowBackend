package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para crear/actualizar tipo de documento")
public record TypeDocumentRequest(
		@NotBlank(message = "El nombre es requerido")
		@Size(max = 60, message = "El nombre no puede exceder 60 caracteres")
		@Schema(description = "Nombre", example = "Cédula")
		String name,

		@Size(max = 10, message = "El código no puede exceder 10 caracteres")
		@Schema(description = "Código (opcional)", example = "CC")
		String code
) {
}
