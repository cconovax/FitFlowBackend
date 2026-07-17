package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información del rol")
public record RoleResponse(
		@Schema(description = "ID del rol", example = "1")
		Long id,

		@Schema(description = "Nombre del rol", example = "ADMIN")
		String name,

		@Schema(description = "Código del rol", example = "ADMIN")
		String code,

		@Schema(description = "ID del gimnasio (opcional)", example = "1")
		Long gymId,

		@Schema(description = "Acceso completo", example = "false")
		Boolean fullAccess,

		@Schema(description = "Es personal del gym (entrenador u otro rol de staff)", example = "false")
		Boolean isStaff,

		@Schema(description = "Estado del rol", example = "true")
		Boolean status
) {
}
