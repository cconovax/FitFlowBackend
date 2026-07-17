package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Dashboard del gym con estadísticas y métricas")
public record GymDashboardResponse(
		GymInfo gymInfo,
		StatsInfo stats,
		List<DailyPoint> dailySessions,
		List<DailySalesPoint> dailySales,
		List<MembershipStat> membershipStats
) {

	@Schema(description = "Información básica del gym")
	public record GymInfo(
			Long id,
			String name,
			String nit,
			String logo,
			String municipalitieName,
			String phone,
			String email,
			Boolean subscriptionActive,
			String subscriptionPlanName,
			LocalDate subscriptionEndDate
	) {}

	@Schema(description = "KPIs del gym")
	public record StatsInfo(
			long totalActiveMembers,
			long totalMembersAllTime,
			long activeMemberships,
			long membershipsThisMonth,
			BigDecimal revenueTotal,
			BigDecimal revenueThisMonth,
			long salesCountThisMonth,
			long sessionsToday,
			long sessionsThisMonth
	) {}

	@Schema(description = "Sesiones por día")
	public record DailyPoint(
			LocalDate date,
			long count
	) {}

	@Schema(description = "Ventas diarias")
	public record DailySalesPoint(
			LocalDate date,
			long count,
			BigDecimal revenue
	) {}

	@Schema(description = "Estadística por tipo de membresía")
	public record MembershipStat(
			String name,
			long total,
			long activeCount
	) {}
}
