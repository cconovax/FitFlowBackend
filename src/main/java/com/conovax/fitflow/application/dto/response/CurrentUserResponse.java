package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Schema(description = "Respuesta con información del usuario autenticado (incluye gyms)")
public record CurrentUserResponse(
		@Schema(description = "ID del usuario", example = "1")
		Long id,

		@Schema(description = "Login del usuario (email o número de documento)", example = "john@example.com")
		String email,

		@Schema(description = "Nombre del usuario", example = "John")
		String firstName,

		@Schema(description = "Apellido del usuario", example = "Doe")
		String lastName,

		@Schema(description = "Estado del usuario", example = "true")
		Boolean enabled,

		@Schema(description = "Fecha de creación")
		LocalDateTime createdAt,

		@Schema(description = "Roles del usuario")
		Set<String> roles,

		@Schema(description = "Permisos del usuario")
		Set<String> permissions,

		@Schema(description = "Gyms asociados al usuario")
		List<GymInfoResponse> gyms
) {
}
