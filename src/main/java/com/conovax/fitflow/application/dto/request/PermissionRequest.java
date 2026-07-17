package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para crear/actualizar permiso")
public record PermissionRequest(
		@NotBlank(message = "El slug es requerido")
		@Size(max = 70, message = "El slug no puede exceder 70 caracteres")
		@Schema(description = "Slug único del permiso", example = "user:read")
		String slug,

		@NotBlank(message = "La descripción es requerida")
		@Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
		@Schema(description = "Descripción", example = "Leer usuarios")
		String description,

		@Schema(description = "Indica si el permiso es básico (true) o privilegiado (false)", example = "true")
		Boolean basic
) {
}
