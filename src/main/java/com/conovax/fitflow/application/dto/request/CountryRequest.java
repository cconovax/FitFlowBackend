package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para crear/actualizar país")
public record CountryRequest(
		@NotBlank(message = "El nombre es requerido")
		@Size(max = 60, message = "El nombre no puede exceder 60 caracteres")
		@Schema(description = "Nombre", example = "Colombia")
		String name
) {
}
