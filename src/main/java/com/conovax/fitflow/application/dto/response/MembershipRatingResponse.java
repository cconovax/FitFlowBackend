package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Respuesta de evaluación de membresía")
public record MembershipRatingResponse(
		Long id,
		Long userGymMembershipId,
		Long coachId,
		LocalDate date,
		BigDecimal weight,
		String observation,
		BigDecimal porcentageFat,
		BigDecimal muscleMass,
		Boolean status
) {
}
