package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de permiso")
public record PermissionResponse(
		@Schema(description = "ID del permiso", example = "1")
		Long id,

		@Schema(description = "Slug único", example = "user:read")
		String slug,

		@Schema(description = "Descripción", example = "Leer usuarios")
		String description,

		@Schema(description = "Básico", example = "true")
		Boolean basic,

		@Schema(description = "Estado", example = "true")
		Boolean status
) {
}
