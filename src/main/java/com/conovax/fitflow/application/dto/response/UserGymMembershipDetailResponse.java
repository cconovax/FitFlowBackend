package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Detalle de membresías asignadas a un usuario-gym")
public record UserGymMembershipDetailResponse(
		Long id,
		Long userGymId,
		Long membershipId,
		BigDecimal paymentPreci,
		LocalDate startDate,
		LocalDate endDate,
		Boolean status,
		MembershipWithBenefitsResponse membership
) {
}
