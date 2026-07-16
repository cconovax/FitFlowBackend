package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Solicitud para registrar o actualizar una suscripción de gym")
public record GymSubscriptionRequest(
		@NotNull(message = "El ID del plan SaaS es requerido")
		@Schema(description = "ID del plan SaaS", example = "1")
		Long saasPlanId,

		@NotNull(message = "La fecha de inicio es requerida")
		@Schema(description = "Fecha de inicio de la suscripción", example = "2026-04-22")
		LocalDate startDate,

		@NotNull(message = "La fecha de fin es requerida")
		@FutureOrPresent(message = "La fecha de fin debe ser hoy o una fecha futura")
		@Schema(description = "Fecha de fin de la suscripción", example = "2027-04-22")
		LocalDate endDate,

		@Schema(description = "Estado administrativo del registro", example = "true")
		Boolean status,

		@Size(max = 255, message = "Las notas no pueden exceder 255 caracteres")
		@Schema(description = "Notas internas", example = "Renovación aprobada por tesorería")
		String notes
) {
}