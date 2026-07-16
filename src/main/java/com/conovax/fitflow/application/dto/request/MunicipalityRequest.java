package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para crear/actualizar municipio")
public record MunicipalityRequest(
		@NotNull(message = "El departamentId es requerido")
		@Positive(message = "El departamentId debe ser mayor que 0")
		@Schema(description = "ID del departamento", example = "1")
		Long departamentId,

		@NotBlank(message = "El nombre es requerido")
		@Size(max = 60, message = "El nombre no puede exceder 60 caracteres")
		@Schema(description = "Nombre", example = "Medellín")
		String name
) {
}
