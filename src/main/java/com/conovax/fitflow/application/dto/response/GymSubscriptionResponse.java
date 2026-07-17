package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Estado o registro de suscripción de un gym")
public record GymSubscriptionResponse(
		@Schema(description = "ID del registro de suscripción", example = "1")
		Long id,

		@Schema(description = "ID del gym", example = "10")
		Long gymId,

		@Schema(description = "ID del plan SaaS", example = "2")
		Long saasPlanId,

		@Schema(description = "Código del plan SaaS", example = "PLAN_PRO_ANUAL")
		String planCode,

		@Schema(description = "Nombre del plan SaaS", example = "Plan Pro Anual")
		String planName,

		@Schema(description = "Fecha de inicio", example = "2026-04-22")
		LocalDate startDate,

		@Schema(description = "Fecha de fin", example = "2027-04-22")
		LocalDate endDate,

		@Schema(description = "Estado administrativo del registro", example = "true")
		Boolean status,

		@Schema(description = "Indica si la suscripción está activa a la fecha actual", example = "true")
		Boolean active,

		@Schema(description = "Días restantes de vigencia", example = "365")
		Long daysRemaining,

		@Schema(description = "Notas internas", example = "Renovación aprobada por tesorería")
		String notes,

		@Schema(description = "Fecha de creación del registro")
		LocalDateTime createdAt,

		@Schema(description = "Fecha de última actualización del registro")
		LocalDateTime updatedAt,

		@Schema(description = "Features incluidas en el plan activo")
		List<SaasPlanFeatureResponse> planFeatures
) {
}