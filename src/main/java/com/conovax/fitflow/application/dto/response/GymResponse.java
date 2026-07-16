package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Respuesta con información del gym")
public record GymResponse(
		@Schema(description = "ID", example = "1")
		Long id,
		@Schema(description = "Nombre", example = "SexBody Gym")
		String name,
		@Schema(description = "NIT", example = "900123456-7")
		String nit,
		@Schema(description = "Logo", example = "https://cdn.example.com/logo.png")
		String logo,
		@Schema(description = "ID del municipio", example = "10")
		Long municipalitieId,
		@Schema(description = "Nombre del municipio", example = "Quibdo")
		String municipalitieName,
		@Schema(description = "Estado", example = "true")
		Boolean status,
		@Schema(description = "Teléfono", example = "+573001234567")
		String phone,
		@Schema(description = "Email", example = "info@sexbody.com")
		String email,
		@Schema(description = "Indica si el gym tiene suscripción activa", example = "true")
		Boolean subscriptionActive,
		@Schema(description = "Nombre del plan vigente o más reciente", example = "Plan Pro Anual")
		String subscriptionPlanName,
		@Schema(description = "Fecha de inicio de la suscripción actual o más reciente", example = "2026-04-22")
		LocalDate subscriptionStartDate,
		@Schema(description = "Fecha de fin de la suscripción actual o más reciente", example = "2027-04-22")
		LocalDate subscriptionEndDate,
		@Schema(description = "ID del gym padre (null si es sede principal)", example = "null")
		Long parentGymId,
		@Schema(description = "Estado de la cuenta del gym: ACTIVE, TRIAL, SUSPENDED", example = "TRIAL")
		String gymStatus,
		@Schema(description = "Fecha en que vence el período de prueba (solo para TRIAL)", example = "2026-05-10T00:00:00")
		LocalDateTime trialExpiresAt
) {
}
