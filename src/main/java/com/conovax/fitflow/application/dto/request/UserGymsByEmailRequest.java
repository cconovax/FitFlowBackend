package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud para obtener gimnasios por email")
public record UserGymsByEmailRequest(
		@NotBlank(message = "El email es requerido")
		@Email(message = "El email no es valido")
		@Schema(description = "Email del usuario", example = "admin@example.com")
		String email
) {
}
