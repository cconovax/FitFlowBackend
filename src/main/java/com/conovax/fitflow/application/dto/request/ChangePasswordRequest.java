package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para cambiar la contraseña del usuario autenticado")
public record ChangePasswordRequest(

		@NotBlank(message = "La contraseña actual es requerida")
		String currentPassword,

		@NotBlank(message = "La nueva contraseña es requerida")
		@Size(min = 6, max = 100, message = "La nueva contraseña debe tener entre 6 y 100 caracteres")
		String newPassword
) {
}
