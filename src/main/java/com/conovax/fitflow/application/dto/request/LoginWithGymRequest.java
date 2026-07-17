package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Solicitud de inicio de sesion con gym seleccionado")
public record LoginWithGymRequest(
		@NotBlank(message = "El login es requerido")
		@Schema(description = "Login del usuario (email o numero de documento)", example = "admin@example.com")
		String email,

		@NotBlank(message = "La contrasena es requerida")
		@Schema(description = "Contrasena del usuario", example = "password123")
		String password,

		@NotNull(message = "El userGymId es requerido")
		@Schema(description = "ID de la relacion usuario-gym", example = "10")
		Long userGymId
) {
}
