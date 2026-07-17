package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Solicitud para asignar entrenador a membresía")
public record MembershipCoachRequest(
		@NotNull(message = "membershipId es obligatorio")
		@Schema(description = "ID de la membresía", example = "1")
		Long membershipId,

		@NotNull(message = "coachId es obligatorio")
		@Schema(description = "ID de la relación usuario-gym del entrenador", example = "22")
		Long coachId,

		@Schema(description = "Estado de la asignación", example = "true")
		Boolean status
) {
}
