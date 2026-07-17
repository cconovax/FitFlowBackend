package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Respuesta con información de un plan SaaS")
public record SaasPlanResponse(
		@Schema(description = "ID del plan", example = "1")
		Long id,

		@Schema(description = "Código técnico del plan", example = "PRO")
		String code,

		@Schema(description = "Nombre comercial del plan", example = "Plan Pro")
		String name,

		@Schema(description = "Descripción del plan", example = "Incluye biometría, inventario y reportes")
		String description,

		@Schema(description = "Precio del plan", example = "149900.00")
		BigDecimal price,

		@Schema(description = "Dias de facturación", example = "30")
        Long numDays,

		@Schema(description = "Estado del plan", example = "true")
		Boolean status,

        @Schema(description = "ID del precio en Stripe (price_xxx). Configurado por el administrador.", example = "price_1OxxxxxYYYYYY")
        String stripePriceId,

		@Schema(description = "Features asignadas al plan")
		List<SaasPlanFeatureResponse> features
) {
}