package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Gym asociado al usuario")
public record GymInfoResponse(
		@Schema(description = "ID de la relación usuario-gym", example = "10")
		Long userGymId,

		@Schema(description = "ID del gym", example = "1")
		Long id,

		@Schema(description = "Nombre del gym", example = "BodyEvolution")
		String name,

		@Schema(description = "Logo del gym (URL)", example = "/uploads/gyms/logo.png")
		String logo,

		@Schema(description = "Municipio del gym", example = "Quibdo")
		String municipalitie,

		@Schema(description = "Indica si el gym tiene una suscripción activa", example = "true")
		Boolean subscriptionActive,

		@Schema(description = "Fecha de vencimiento de la suscripción actual", example = "2027-04-22")
		LocalDate subscriptionEndDate
) {
}
