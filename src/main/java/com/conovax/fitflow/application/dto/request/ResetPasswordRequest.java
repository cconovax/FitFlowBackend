package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Cambio de contraseña con token de restablecimiento")
public record ResetPasswordRequest(
		@NotBlank(message = "El token es requerido")
		@Schema(description = "Token recibido por email")
		String token,

		@NotBlank(message = "La nueva contraseña es requerida")
		@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
		@Schema(description = "Nueva contraseña", example = "MiNueva@Pass123")
		String newPassword
) {}
