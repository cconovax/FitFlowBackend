package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información de una feature SaaS")
public record SaasFeatureResponse(
		@Schema(description = "ID de la feature", example = "1")
		Long id,

		@Schema(description = "Código técnico de la feature", example = "BIOMETRIC_ACCESS")
		String code,

		@Schema(description = "Nombre comercial de la feature", example = "Acceso biométrico")
		String name,

		@Schema(description = "Descripción", example = "Permite registrar y validar huella digital")
		String description,

		@Schema(description = "Tipo de feature", example = "MODULE")
		String featureType,

		@Schema(description = "Estado de la feature", example = "true")
		Boolean status
) {
}