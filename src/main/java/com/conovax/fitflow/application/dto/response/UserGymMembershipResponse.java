package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Respuesta de membresía asignada a usuario")
public record UserGymMembershipResponse(
		Long id,
		Long userGymId,
		Long membershipId,
		BigDecimal paymentPreci,
		LocalDate startDate,
		LocalDate endDate,
		Boolean status
) {
}
