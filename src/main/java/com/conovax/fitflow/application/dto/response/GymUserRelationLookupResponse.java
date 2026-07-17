package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Datos de relación de un usuario existente (gyms y roles asignados) para precargar el modal de relacionar")
public record GymUserRelationLookupResponse(
		@Schema(description = "Número de documento del usuario", example = "1020304050")
		String numDocument,

		@Schema(description = "Nombres del usuario", example = "John")
		String names,

		@Schema(description = "Apellidos del usuario", example = "Doe")
		String surnames,

		@Schema(description = "IDs de gyms a los que el usuario está relacionado (activos)", example = "[1,2,3]")
		List<Long> gymIds,

		@Schema(description = "IDs de roles asignados al usuario en sus relaciones activas", example = "[1,2]")
		List<Long> roleIds
) {
}
