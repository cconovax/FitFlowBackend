package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Dashboard completo del cliente del gym")
public record ClientDashboardResponse(
		String userName,
		String userSurnames,
		String userEmail,
		ActiveMembershipInfo activeMembership,
		AttendanceInfo attendance,
		List<MembershipRatingResponse> ratings,
		MembershipResultResponse result
) {

	@Schema(description = "Información de la membresía activa del usuario")
	public record ActiveMembershipInfo(
			Long userGymMembershipId,
			Long membershipId,
			String name,
			String description,
			LocalDate startDate,
			LocalDate endDate,
			long daysRemaining,
			int totalDays,
			int elapsedPercent,
			List<String> benefits
	) {}

	@Schema(description = "Estadísticas de asistencia del usuario")
	public record AttendanceInfo(
			long totalSessions,
			long sessionsThisMonth,
			int currentStreak,
			List<AttendanceDay> heatmap
	) {}

	@Schema(description = "Día de asistencia para el mapa de calor")
	public record AttendanceDay(
			LocalDate date,
			long count
	) {}
}
