package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para actualizar el perfil del usuario autenticado")
public record UpdateProfileRequest(

		@NotBlank(message = "Los nombres son requeridos")
		@Size(max = 60, message = "Los nombres no pueden exceder 60 caracteres")
		String names,

		@NotBlank(message = "Los apellidos son requeridos")
		@Size(max = 60, message = "Los apellidos no pueden exceder 60 caracteres")
		String surnames,

		@Size(max = 13, message = "El teléfono no puede exceder 13 caracteres")
		String phone,

		@Email(message = "El email debe ser válido")
		@Size(max = 60, message = "El email no puede exceder 60 caracteres")
		String email,

		@NotBlank(message = "El número de documento es requerido")
		@Size(max = 15, message = "El número de documento no puede exceder 15 caracteres")
		String numDocument,

		@NotNull(message = "El typeDocumentId es requerido")
		@Positive(message = "El typeDocumentId debe ser mayor que 0")
		Long typeDocumentId,

		@NotNull(message = "El sexoId es requerido")
		@Positive(message = "El sexoId debe ser mayor que 0")
		Long sexoId,

		@NotNull(message = "El municipalitieId es requerido")
		@Positive(message = "El municipalitieId debe ser mayor que 0")
		Long municipalitieId
) {
}
