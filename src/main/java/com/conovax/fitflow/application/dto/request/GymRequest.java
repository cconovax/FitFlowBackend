package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para crear/actualizar un gym")
public record GymRequest(
		@NotBlank(message = "El nombre es requerido")
		@Size(max = 60, message = "El nombre no puede exceder 60 caracteres")
		@Schema(description = "Nombre", example = "SexBody Gym")
		String name,

		@NotBlank(message = "El NIT es requerido")
		@Size(max = 20, message = "El NIT no puede exceder 20 caracteres")
		@Schema(description = "NIT", example = "900123456-7")
		String nit,

		@Schema(description = "Logo (URL o ruta). Opcional en update; si no se envía, se conserva el actual", example = "https://cdn.example.com/logo.png")
		String logo,

		@NotNull(message = "El ID del municipio es requerido")
		@Schema(description = "ID del municipio", example = "10")
		Long municipalitieId,

		@Size(max = 13, message = "El teléfono no puede exceder 13 caracteres")
		@Schema(description = "Teléfono", example = "+573001234567")
		String phone,

		@Email(message = "El email no tiene un formato válido")
		@Size(max = 100, message = "El email no puede exceder 100 caracteres")
		@Schema(description = "Email", example = "info@sexbody.com")
		String email
) {
}
