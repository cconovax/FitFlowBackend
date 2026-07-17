package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Usuario listado por gym (relación users_gyms + users + peoples), sin contraseña")
public record UserGymUserResponse(
		@Schema(description = "ID de la persona (peoples.people_id)", example = "7")
		Long peopleId,

		@Schema(description = "Número de documento", example = "1020304050")
		String numDocument,

		@Schema(description = "Nombres", example = "John")
		String names,

		@Schema(description = "Apellidos", example = "Doe")
		String surnames,

		@Schema(description = "Teléfono", example = "+573001112233")
		String phone,

		@Schema(description = "Email", example = "john@example.com")
		String email,

		@Schema(description = "Estado de la relación usuario-gym (users_gyms.status)")
		Boolean userGymStatus,

		@Schema(description = "ID del usuario (users.user_id)", example = "12")
		Long userId,

		@Schema(description = "ID de la relación usuario-gym (users_gyms.user_gym_id)", example = "55")
		Long userGymId
) {
}
