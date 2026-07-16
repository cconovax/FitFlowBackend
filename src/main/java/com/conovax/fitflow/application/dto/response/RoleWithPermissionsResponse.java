package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Respuesta con información del rol y sus permisos")
public record RoleWithPermissionsResponse(
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

		@Schema(description = "Estado (activo/inactivo)", example = "true")
		Boolean status,

		@Schema(description = "IDs de permisos asignados al rol", example = "[1, 2, 3]")
		List<Long> permissionIds
) {
}
