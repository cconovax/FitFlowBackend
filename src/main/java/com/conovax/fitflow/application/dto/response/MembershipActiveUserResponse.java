package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Respuesta de usuarios con membresía activa")
public record MembershipActiveUserResponse(
		Long userGymMembershipId,
		Long userGymId,
		Long membershipId,
		String userName,
		String email,
		String phone,
		LocalDate startDate,
		LocalDate endDate,
		Boolean status
) {
}
