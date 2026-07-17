package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Respuesta de autenticacion con gym seleccionado")
public record AuthGymResponse(
		@Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
		String token,

		@Schema(description = "Tipo de token", example = "Bearer")
		String type,

		@Schema(description = "ID del usuario", example = "1")
		Long id,

		@Schema(description = "Nombres del usuario", example = "John")
		String names,

		@Schema(description = "Foto del usuario (URL/ruta)", example = "/uploads/logo/profile-default.svg")
		String photo,

		@Schema(description = "Roles del usuario")
		List<AuthRoleResponse> roles,

		@Schema(description = "Permisos del usuario")
		List<String> permissions,

		@Schema(description = "Indica si el gym seleccionado tiene suscripción activa", example = "true")
		Boolean subscriptionActive,

		@Schema(description = "Fecha de vencimiento de la suscripción del gym seleccionado", example = "2027-04-22")
		LocalDate subscriptionEndDate,

		@Schema(description = "Gym seleccionado")
		List<GymInfoResponse> gyms
) {
	public AuthGymResponse(
			String token,
			String names,
			Long id,
			String photo,
			List<AuthRoleResponse> roles,
			List<String> permissions,
			Boolean subscriptionActive,
			LocalDate subscriptionEndDate,
			List<GymInfoResponse> gyms
	) {
		this(token, "Bearer", id, names, photo, roles, permissions, subscriptionActive, subscriptionEndDate, gyms);
	}
}
