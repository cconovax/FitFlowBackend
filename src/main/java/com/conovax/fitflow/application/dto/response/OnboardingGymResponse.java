package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Respuesta del onboarding: el gym creado y la relación usuario-gym")
public record OnboardingGymResponse(
		@Schema(description = "ID del gym creado", example = "42")
		Long gymId,

		@Schema(description = "Nombre del gym", example = "Mi Gym")
		String gymName,

		@Schema(description = "Estado del gym: TRIAL", example = "TRIAL")
		String gymStatus,

		@Schema(description = "Fecha de vencimiento del período de prueba", example = "2026-06-01T00:00:00")
		LocalDateTime trialExpiresAt,

		@Schema(description = "ID de la relación usuario-gym (userGymId)", example = "15")
		Long userGymId
) {}
