package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Solicitud para crear una evaluación de membresía")
public record MembershipRatingRequest(
		@NotNull(message = "userGymMembershipId es obligatorio")
		@Schema(description = "ID de la relación usuario-membresía", example = "12")
		Long userGymMembershipId,

		@NotNull(message = "coachId es obligatorio")
		@Schema(description = "ID del entrenador (user_gym_id)", example = "22")
		Long coachId,

		@NotNull(message = "weight es obligatorio")
		@DecimalMin(value = "0.0", inclusive = false, message = "weight debe ser mayor que 0")
		@Digits(integer = 6, fraction = 0, message = "weight debe ser un número válido")
		@Schema(description = "Peso", example = "72")
		BigDecimal weight,

		@NotBlank(message = "observation es obligatorio")
		@Size(max = 200, message = "observation no puede exceder 200 caracteres")
		@Schema(description = "Observaciones", example = "Mejora en resistencia")
		String observation,

		@NotNull(message = "porcentageFat es obligatorio")
		@DecimalMin(value = "0.0", inclusive = false, message = "porcentageFat debe ser mayor que 0")
		@Digits(integer = 3, fraction = 0, message = "porcentageFat debe ser un número válido")
		@Schema(description = "Porcentaje de grasa", example = "18")
		BigDecimal porcentageFat,

		@NotNull(message = "muscleMass es obligatorio")
		@DecimalMin(value = "0.0", inclusive = false, message = "muscleMass debe ser mayor que 0")
		@Digits(integer = 3, fraction = 0, message = "muscleMass debe ser un número válido")
		@Schema(description = "Masa muscular", example = "45")
		BigDecimal muscleMass,

		@Schema(description = "Estado de la evaluación", example = "true")
		Boolean status
) {
}
