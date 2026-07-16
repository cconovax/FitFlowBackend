package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Solicitud para crear o actualizar el resultado de la membresía")
public record MembershipResultRequest(
		@NotNull(message = "userGymMembershipId es obligatorio")
		@Schema(description = "ID de la relación usuario-membresía", example = "12")
		Long userGymMembershipId,

		@NotNull(message = "coachId es obligatorio")
		@Schema(description = "ID del entrenador (user_gym_id)", example = "22")
		Long coachId,

		@NotNull(message = "startWeight es obligatorio")
		@DecimalMin(value = "0.0", inclusive = false, message = "startWeight debe ser mayor que 0")
		@Digits(integer = 6, fraction = 0, message = "startWeight debe ser un número válido")
		@Schema(description = "Peso inicial", example = "78")
		BigDecimal startWeight,

		@NotNull(message = "endWeight es obligatorio")
		@DecimalMin(value = "0.0", inclusive = false, message = "endWeight debe ser mayor que 0")
		@Digits(integer = 6, fraction = 0, message = "endWeight debe ser un número válido")
		@Schema(description = "Peso final", example = "74")
		BigDecimal endWeight,

		@NotNull(message = "startFat es obligatorio")
		@DecimalMin(value = "0.0", inclusive = false, message = "startFat debe ser mayor que 0")
		@Digits(integer = 3, fraction = 0, message = "startFat debe ser un número válido")
		@Schema(description = "Grasa inicial", example = "22")
		BigDecimal startFat,

		@NotNull(message = "endFat es obligatorio")
		@DecimalMin(value = "0.0", inclusive = false, message = "endFat debe ser mayor que 0")
		@Digits(integer = 3, fraction = 0, message = "endFat debe ser un número válido")
		@Schema(description = "Grasa final", example = "18")
		BigDecimal endFat,

		@NotNull(message = "startMuscleMass es obligatorio")
		@DecimalMin(value = "0.0", inclusive = false, message = "startMuscleMass debe ser mayor que 0")
		@Digits(integer = 3, fraction = 0, message = "startMuscleMass debe ser un número válido")
		@Schema(description = "Masa muscular inicial", example = "41")
		BigDecimal startMuscleMass,

		@NotNull(message = "endMuscleMass es obligatorio")
		@DecimalMin(value = "0.0", inclusive = false, message = "endMuscleMass debe ser mayor que 0")
		@Digits(integer = 3, fraction = 0, message = "endMuscleMass debe ser un número válido")
		@Schema(description = "Masa muscular final", example = "46")
		BigDecimal endMuscleMass,

		@Schema(description = "Fecha de creación", example = "2026-05-31")
		LocalDate createdAt,

		@Schema(description = "Estado del resultado", example = "true")
		Boolean status
) {
}
