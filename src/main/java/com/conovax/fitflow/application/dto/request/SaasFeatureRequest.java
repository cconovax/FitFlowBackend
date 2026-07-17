package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para crear o actualizar una feature SaaS")
public record SaasFeatureRequest(
		@NotBlank(message = "El código de la feature es requerido")
		@Size(max = 60, message = "El código de la feature no puede exceder 60 caracteres")
		@Schema(description = "Código técnico de la feature", example = "BIOMETRIC_ACCESS")
		String code,

		@NotBlank(message = "El nombre de la feature es requerido")
		@Size(max = 80, message = "El nombre de la feature no puede exceder 80 caracteres")
		@Schema(description = "Nombre comercial de la feature", example = "Acceso biométrico")
		String name,

		@Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
		@Schema(description = "Descripción de la feature", example = "Permite registrar y validar huella digital")
		String description,

		@NotBlank(message = "El tipo de feature es requerido")
		@Size(max = 30, message = "El tipo de feature no puede exceder 30 caracteres")
		@Schema(description = "Tipo de feature", example = "MODULE")
		String featureType,

		@Schema(description = "Estado de la feature", example = "true")
		Boolean status
) {
}