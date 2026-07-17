package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.response.GymDashboardResponse;
import com.conovax.fitflow.application.dto.response.GymSubscriptionResponse;
import com.conovax.fitflow.domain.entities.Gym;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.GymRepository;
import com.conovax.fitflow.domain.repositories.MunicipalityRepository;
import com.conovax.fitflow.infrastructure.persistence.repositories.SaleJpaRepository;
import com.conovax.fitflow.infrastructure.persistence.repositories.SessionLogJpaRepository;
import com.conovax.fitflow.infrastructure.persistence.repositories.UserGymMembershipJpaRepository;
import com.conovax.fitflow.infrastructure.persistence.repositories.UsersGymJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GymDashboardService {

	private final GymRepository gymRepository;
	private final MunicipalityRepository municipalityRepository;
	private final GymSubscriptionService gymSubscriptionService;
	private final UsersGymJpaRepository usersGymJpaRepository;
	private final UserGymMembershipJpaRepository userGymMembershipJpaRepository;
	private final SaleJpaRepository saleJpaRepository;
	private final SessionLogJpaRepository sessionLogJpaRepository;

	@Transactional(readOnly = true)
	public GymDashboardResponse getDashboard(Long gymId) {
		Gym gym = gymRepository.findByIdAndStatusTrue(gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + gymId));

		String municipalitieName = municipalityRepository.findByIdAndStatusTrue(gym.getMunicipalitieId())
				.map(m -> m.getName())
				.orElse(null);

		GymSubscriptionResponse sub = gymSubscriptionService.getCurrentByGymId(gymId);

		// ── Date helpers ──────────────────────────────────────────────
		LocalDate today = LocalDate.now();
		LocalDate firstOfMonth = today.withDayOfMonth(1);
		LocalDate lastOfMonth = today.withDayOfMonth(today.lengthOfMonth());
		LocalDate thirtyDaysAgo = today.minusDays(29);

		LocalDateTime monthStart = firstOfMonth.atStartOfDay();
		LocalDateTime monthEnd = lastOfMonth.atTime(LocalTime.MAX);
		LocalDateTime chartStart = thirtyDaysAgo.atStartOfDay();
		LocalDateTime chartEnd = today.atTime(LocalTime.MAX);

		// ── Stats ─────────────────────────────────────────────────────
		long totalActiveMembers = usersGymJpaRepository.countActiveByGymId(gymId);
		long totalMembersAllTime = usersGymJpaRepository.countTotalByGymId(gymId);
		long activeMemberships = userGymMembershipJpaRepository.countActiveMembershipsByGymId(gymId, today);
		long membershipsThisMonth = userGymMembershipJpaRepository.countMembershipsByGymIdAndDateRange(
				gymId, firstOfMonth, lastOfMonth);

		BigDecimal revenueTotal = saleJpaRepository.sumTotalRevenueByGymId(gymId);
		BigDecimal revenueThisMonth = saleJpaRepository.sumRevenueByGymIdAndDateRange(gymId, monthStart, monthEnd);
		long salesCountThisMonth = saleJpaRepository.countSalesByGymIdAndDateRange(gymId, monthStart, monthEnd);

		long sessionsToday = sessionLogJpaRepository.countSessionsTodayByGymId(gymId, today);
		long sessionsThisMonth = sessionLogJpaRepository.countSessionsByGymIdAndDateRange(
				gymId, firstOfMonth, lastOfMonth);

		GymDashboardResponse.StatsInfo stats = new GymDashboardResponse.StatsInfo(
				totalActiveMembers,
				totalMembersAllTime,
				activeMemberships,
				membershipsThisMonth,
				revenueTotal != null ? revenueTotal : BigDecimal.ZERO,
				revenueThisMonth != null ? revenueThisMonth : BigDecimal.ZERO,
				salesCountThisMonth,
				sessionsToday,
				sessionsThisMonth
		);

		// ── Daily sessions (last 30 days) ─────────────────────────────
		List<Object[]> rawSessions = sessionLogJpaRepository.findDailySessionsByGymId(
				gymId, thirtyDaysAgo, today);
		List<GymDashboardResponse.DailyPoint> dailySessions = new ArrayList<>();
		for (Object[] row : rawSessions) {
			LocalDate date = toLocalDate(row[0]);
			long cnt = toLong(row[1]);
			dailySessions.add(new GymDashboardResponse.DailyPoint(date, cnt));
		}

		// ── Daily sales (last 30 days) ────────────────────────────────
		List<Object[]> rawSales = saleJpaRepository.findDailySalesByGymId(gymId, chartStart, chartEnd);
		List<GymDashboardResponse.DailySalesPoint> dailySales = new ArrayList<>();
		for (Object[] row : rawSales) {
			LocalDate date = toLocalDate(row[0]);
			long cnt = toLong(row[1]);
			BigDecimal revenue = row[2] instanceof BigDecimal bd ? bd : new BigDecimal(row[2].toString());
			dailySales.add(new GymDashboardResponse.DailySalesPoint(date, cnt, revenue));
		}

		// ── Membership distribution ───────────────────────────────────
		List<Object[]> rawDist = userGymMembershipJpaRepository.findMembershipDistributionByGymId(gymId, today);
		List<GymDashboardResponse.MembershipStat> membershipStats = new ArrayList<>();
		for (Object[] row : rawDist) {
			String name = row[0] != null ? row[0].toString() : "Desconocida";
			long total = toLong(row[1]);
			long activeCount = toLong(row[2]);
			membershipStats.add(new GymDashboardResponse.MembershipStat(name, total, activeCount));
		}

		// ── Gym info ──────────────────────────────────────────────────
		GymDashboardResponse.GymInfo gymInfo = new GymDashboardResponse.GymInfo(
				gym.getId(),
				gym.getName(),
				gym.getNit(),
				gym.getLogo(),
				municipalitieName,
				gym.getPhone(),
				gym.getEmail(),
				sub.active(),
				sub.planName(),
				sub.endDate()
		);

		return new GymDashboardResponse(gymInfo, stats, dailySessions, dailySales, membershipStats);
	}

	private LocalDate toLocalDate(Object obj) {
		if (obj instanceof LocalDate ld) return ld;
		if (obj instanceof java.sql.Date sd) return sd.toLocalDate();
		if (obj instanceof String s) return LocalDate.parse(s);
		return LocalDate.now();
	}

	private long toLong(Object obj) {
		if (obj instanceof Number n) return n.longValue();
		return 0L;
	}
}
