package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para crear/actualizar moneda")
public record CurrencyRequest(
		@NotBlank(message = "El nombre es requerido")
		@Size(max = 30, message = "El nombre no puede exceder 30 caracteres")
		@Schema(description = "Nombre de la moneda", example = "Peso Colombiano")
		String name,

		@NotBlank(message = "El codigo es requerido")
		@Size(max = 10, message = "El codigo no puede exceder 10 caracteres")
		@Schema(description = "Codigo de la moneda", example = "COP")
		String code
) {
}