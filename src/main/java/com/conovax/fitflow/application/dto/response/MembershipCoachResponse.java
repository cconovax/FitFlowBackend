package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de asignación membresía-entrenador")
public record MembershipCoachResponse(
		Long id,
		Long membershipId,
		String membershipName,
		Long coachId,
		String coachName,
		Boolean status
) {
}
