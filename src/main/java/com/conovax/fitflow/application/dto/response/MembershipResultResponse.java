package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Respuesta de resultado de membresía")
public record MembershipResultResponse(
		Long id,
		Long userGymMembershipId,
		Long coachId,
		BigDecimal startWeight,
		BigDecimal endWeight,
		BigDecimal startFat,
		BigDecimal endFat,
		BigDecimal startMuscleMass,
		BigDecimal endMuscleMass,
		LocalDate createdAt,
		Boolean status
) {
}
