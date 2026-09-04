package com.conovax.fitflow.shared.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud de restablecimiento de contraseña")
public record ForgotPasswordRequest(
		@NotBlank(message = "El email es requerido")
		@Email(message = "El email debe ser válido")
		@Schema(description = "Correo electrónico de la cuenta", example = "john@example.com")
		String email
) {}
