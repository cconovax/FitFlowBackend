package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Schema(description = "Solicitud para registrar un usuario (People + User) y referenciarlo a múltiples gyms")
public record UserRegisterWithGymsRequest(
		@Schema(description = "Correo electrónico (opcional). Si no se envía, el login será el número de documento.", example = "john@example.com")
		@Email(message = "El email debe ser válido")
		@Size(max = 60, message = "El email no puede exceder 60 caracteres")
		String email,

		@NotBlank(message = "Los nombres son requeridos")
		@Schema(description = "Nombres de la persona", example = "John")
		@Size(max = 60, message = "Los nombres no pueden exceder 60 caracteres")
		String names,

		@NotBlank(message = "Los apellidos son requeridos")
		@Schema(description = "Apellidos de la persona", example = "Doe")
		@Size(max = 60, message = "Los apellidos no pueden exceder 60 caracteres")
		String surnames,

		@Schema(description = "Teléfono (opcional)", example = "+573001112233")
		@Size(max = 13, message = "El teléfono no puede exceder 13 caracteres")
		String phone,

		@Schema(description = "Ruta/URL de la foto (opcional). Si no se envía, se asigna una por defecto", example = "/uploads/logo/profile-default.svg")
		@Size(max = 60, message = "La foto no puede exceder 60 caracteres")
		String photo,

		@NotBlank(message = "El número de documento es requerido")
		@Schema(description = "Número de documento", example = "1020304050")
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

		@Schema(description = "Contraseña (opcional). Si no se envía, se usa numDocument.", example = "password123")
		@Size(max = 100, message = "La contraseña no puede exceder 100 caracteres")
		String password,


		@NotNull(message = "La lista de gyms es requerida")
		@Size(min = 1, message = "Debe enviar al menos un gymId")
		@Schema(description = "IDs de gyms a los que se referenciará el usuario", example = "[1, 2, 3]")
		Set<Long> gymIds,

		@NotNull(message = "La lista de roles es requerida")
		@Size(min = 1, message = "Debe enviar al menos un roleId")
		@Schema(description = "IDs de roles asignados al usuario", example = "[1, 2]")
		Set<Long> roleIds
) {
}
