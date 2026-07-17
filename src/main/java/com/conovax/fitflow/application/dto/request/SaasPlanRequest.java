package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Solicitud para crear o actualizar un plan SaaS")
public record SaasPlanRequest(
		@NotBlank(message = "El código del plan es requerido")
		@Size(max = 40, message = "El código del plan no puede exceder 40 caracteres")
		@Schema(description = "Código técnico del plan", example = "PRO")
		String code,

		@NotBlank(message = "El nombre del plan es requerido")
		@Size(max = 80, message = "El nombre del plan no puede exceder 80 caracteres")
		@Schema(description = "Nombre comercial del plan", example = "Plan Pro")
		String name,

		@Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
		@Schema(description = "Descripción del plan", example = "Incluye biometría, inventario y reportes")
		String description,

		@NotNull(message = "El precio del plan es requerido")
		@DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor que 0")
		@Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 2 decimales")
		@Schema(description = "Precio del plan", example = "149900.00")
		BigDecimal price,

		@NotNull(message = "El numero de dia de facturación es requerido")
		@Max(message = "El maximo numero de dias es de 1000", value = 1000)
        @Min(message = "El numero minimo de dias es de 1", value = 1)
		@Schema(description = "Numero de dias de la facturación", example = "30 que hace referencia a mensual")
		Long numDays,

        @Size(max = 100, message = "El Stripe Price ID no puede exceder 100 caracteres")
        @Schema(description = "ID del precio en Stripe (price_xxx). Requerido para procesar pagos.", example = "price_1OxxxxxYYYYYY")
        String stripePriceId,

		@Schema(description = "Estado del plan (activo/inactivo). Por defecto true.", example = "true")
		Boolean status,

		@Valid
		@Schema(description = "Features asignadas al plan")
		List<SaasPlanFeatureRequest> features
) {
}