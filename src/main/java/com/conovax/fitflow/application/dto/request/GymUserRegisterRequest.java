package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Schema(description = "Solicitud para registrar un usuario y referenciarlo a un gym (sin contraseña; usa numDocument)")
public record GymUserRegisterRequest(
		@Schema(description = "Correo electrónico (opcional)", example = "john@example.com")
		@Email(message = "El email debe ser válido")
		@Size(max = 60, message = "El email no puede exceder 60 caracteres")
		String email,

		@NotBlank(message = "Los nombres son requeridos")
		@Schema(description = "Nombres", example = "John")
		@Size(max = 60, message = "Los nombres no pueden exceder 60 caracteres")
		String names,

		@NotBlank(message = "Los apellidos son requeridos")
		@Schema(description = "Apellidos", example = "Doe")
		@Size(max = 60, message = "Los apellidos no pueden exceder 60 caracteres")
		String surnames,

		@Schema(description = "Teléfono (opcional)", example = "+573001112233")
		@Size(max = 13, message = "El teléfono no puede exceder 13 caracteres")
		String phone,

		@Schema(description = "Foto (opcional). Si no se envía, se asigna una por defecto", example = "/uploads/logo/profile-default.svg")
		@Size(max = 60, message = "La foto no puede exceder 60 caracteres")
		String photo,

		@NotBlank(message = "El número de documento es requerido")
		@Schema(description = "Número de documento (también será la contraseña por defecto)", example = "1020304050")
		@Size(max = 15, message = "El número de documento no puede exceder 15 caracteres")
		String numDocument,

		@Schema(description = "ID del municipio", example = "1")
		@NotNull(message = "El municipalitieId es requerido")
		@Positive(message = "El municipalitieId debe ser mayor que 0")
		Long municipalitieId,

		@Schema(description = "ID del sexo", example = "1")
		@NotNull(message = "El sexoId es requerido")
		@Positive(message = "El sexoId debe ser mayor que 0")
		Long sexoId,

		@Schema(description = "ID del tipo de documento", example = "1")
		@NotNull(message = "El typeDocumentId es requerido")
		@Positive(message = "El typeDocumentId debe ser mayor que 0")
		Long typeDocumentId,

		@NotNull(message = "El fingerprint es requerido")
		@Schema(
				description = "Huella del usuario (bytes). En JSON se envía como Base64.",
				example = "AAECAwQFBgcICQoLDA0ODw=="
		)
		@Size(min = 1, message = "El fingerprint no puede estar vacío")
		byte[] fingerprint,

		@Schema(description = "IDs de roles para asignar (opcional)", example = "[2]")
		Set<Long> roleIds
) {
}
