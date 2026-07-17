package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para crear/actualizar un beneficio")
public record BenefitRequest(
		@NotBlank(message = "El nombre es obligatorio")
		@Size(max = 80, message = "El nombre no puede exceder 80 caracteres")
		@Schema(description = "Nombre", example = "Acceso a sauna")
		String name,

		@NotBlank(message = "La descripción es obligatoria")
		@Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
		@Schema(description = "Descripción", example = "Incluye acceso a sauna 2 veces por semana")
		String description,

		@Positive(message = "El gymId debe ser mayor que 0")
		@Schema(description = "ID del gimnasio (opcional si es global)", example = "1")
		Long gymId
) {
}
