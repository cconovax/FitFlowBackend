package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.response.ClientDashboardResponse;
import com.conovax.sexbody.application.dto.response.MembershipRatingResponse;
import com.conovax.sexbody.application.dto.response.MembershipResultResponse;
import com.conovax.sexbody.domain.entities.Benefit;
import com.conovax.sexbody.domain.entities.MembershipBenefit;
import com.conovax.sexbody.domain.entities.MembershipRating;
import com.conovax.sexbody.domain.entities.MembershipResult;
import com.conovax.sexbody.domain.entities.SessionLog;
import com.conovax.sexbody.domain.entities.UserGymMembership;
import com.conovax.sexbody.domain.entities.UserGymUserPeople;
import com.conovax.sexbody.domain.entities.UsersGym;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.BenefitRepository;
import com.conovax.sexbody.domain.repositories.MembershipBenefitRepository;
import com.conovax.sexbody.domain.repositories.MembershipRatingRepository;
import com.conovax.sexbody.domain.repositories.MembershipRepository;
import com.conovax.sexbody.domain.repositories.MembershipResultRepository;
import com.conovax.sexbody.domain.repositories.SessionLogRepository;
import com.conovax.sexbody.domain.repositories.UserGymMembershipRepository;
import com.conovax.sexbody.domain.repositories.UsersGymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientDashboardService {

	private final UsersGymRepository usersGymRepository;
	private final UserGymMembershipRepository userGymMembershipRepository;
	private final MembershipRepository membershipRepository;
	private final MembershipBenefitRepository membershipBenefitRepository;
	private final BenefitRepository benefitRepository;
	private final MembershipRatingRepository membershipRatingRepository;
	private final MembershipResultRepository membershipResultRepository;
	private final SessionLogRepository sessionLogRepository;

	@Transactional(readOnly = true)
	public ClientDashboardResponse getDashboard(Long gymId, Long userId) {

		// 1. Resolve user-gym relation
		UsersGym userGym = usersGymRepository.findByUserIdAndGymId(userId, gymId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"El usuario no está asociado a este gym"));
		Long userGymId = userGym.getId();

		// 2. Get user personal info
		UserGymUserPeople userPeople = usersGymRepository.findUserGymPeopleById(userGymId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Datos del usuario no encontrados"));

		// 3. Find the current active membership
		LocalDate today = LocalDate.now();
		Optional<UserGymMembership> activeMembershipOpt = userGymMembershipRepository
				.findAllByUserGymIdOrderByStartDateDescIdDesc(userGymId)
				.stream()
				.filter(ugm -> Boolean.TRUE.equals(ugm.getStatus())
						&& ugm.getEndDate() != null
						&& !ugm.getEndDate().isBefore(today))
				.findFirst();

		// 4. Build membership info, ratings and result
		ClientDashboardResponse.ActiveMembershipInfo membershipInfo = null;
		List<MembershipRatingResponse> ratings = List.of();
		MembershipResultResponse result = null;

		if (activeMembershipOpt.isPresent()) {
			UserGymMembership ugm = activeMembershipOpt.get();
			Long ugmId = ugm.getId();
			Long membershipId = ugm.getMembershipId();

			// Membership details
			String membershipName = "Membresía";
			String membershipDesc = "";
			if (membershipId != null) {
				var membership = membershipRepository.findByIdAndStatusTrue(membershipId);
				if (membership.isPresent()) {
					membershipName = membership.get().getName();
					membershipDesc = membership.get().getDescription();
				}
			}

			// Benefits
			List<String> benefitNames = List.of();
			if (membershipId != null) {
				List<Long> benefitIds = membershipBenefitRepository
						.findAllByMembershipIdIn(List.of(membershipId))
						.stream()
						.map(MembershipBenefit::getBenefitId)
						.toList();
				if (!benefitIds.isEmpty()) {
					benefitNames = benefitRepository.findAllByIdInAndStatusTrue(benefitIds)
							.stream()
							.map(Benefit::getName)
							.toList();
				}
			}

			// Elapsed metrics
			LocalDate startDate = ugm.getStartDate();
			LocalDate endDate = ugm.getEndDate();
			long totalDays = startDate != null && endDate != null
					? Math.max(1, ChronoUnit.DAYS.between(startDate, endDate))
					: 1;
			long elapsed = startDate != null ? Math.max(0, ChronoUnit.DAYS.between(startDate, today)) : 0;
			long daysRemaining = endDate != null ? Math.max(0, ChronoUnit.DAYS.between(today, endDate)) : 0;
			int elapsedPercent = (int) Math.min(100, elapsed * 100L / totalDays);

			membershipInfo = new ClientDashboardResponse.ActiveMembershipInfo(
					ugmId,
					membershipId,
					membershipName,
					membershipDesc,
					startDate,
					endDate,
					daysRemaining,
					(int) totalDays,
					elapsedPercent,
					benefitNames
			);

			// Ratings history
			ratings = membershipRatingRepository
					.findAllByUserGymMembershipIdOrderByDateDescIdDesc(ugmId)
					.stream()
					.map(this::toRatingResponse)
					.toList();

			// Membership result (before/after)
			result = membershipResultRepository.findByUserGymMembershipId(ugmId)
					.map(this::toResultResponse)
					.orElse(null);
		}

		// 5. Build attendance info from session logs
		List<SessionLog> allSessions = sessionLogRepository.findAllByUserGymIdOrderByStartDateDesc(userGymId);
		ClientDashboardResponse.AttendanceInfo attendanceInfo = buildAttendanceInfo(allSessions, today);

		return new ClientDashboardResponse(
				userPeople.getNames(),
				userPeople.getSurnames(),
				userPeople.getEmail(),
				membershipInfo,
				attendanceInfo,
				ratings,
				result
		);
	}

	private ClientDashboardResponse.AttendanceInfo buildAttendanceInfo(List<SessionLog> sessions, LocalDate today) {
		long totalSessions = sessions.size();

		// Sessions this month
		LocalDate firstOfMonth = today.withDayOfMonth(1);
		LocalDate lastOfMonth = today.withDayOfMonth(today.lengthOfMonth());
		long sessionsThisMonth = sessions.stream()
				.filter(s -> s.getStartDate() != null
						&& !s.getStartDate().isBefore(firstOfMonth)
						&& !s.getStartDate().isAfter(lastOfMonth))
				.count();

		// Current streak (consecutive days from today going backwards)
		Set<LocalDate> sessionDates = sessions.stream()
				.filter(s -> s.getStartDate() != null)
				.map(SessionLog::getStartDate)
				.collect(Collectors.toSet());

		int streak = 0;
		LocalDate checkDate = today;
		while (sessionDates.contains(checkDate)) {
			streak++;
			checkDate = checkDate.minusDays(1);
		}

		// Heatmap: last 84 days (12 weeks)
		LocalDate heatmapStart = today.minusDays(83);
		Map<LocalDate, Long> countByDate = sessions.stream()
				.filter(s -> s.getStartDate() != null
						&& !s.getStartDate().isBefore(heatmapStart)
						&& !s.getStartDate().isAfter(today))
				.collect(Collectors.groupingBy(SessionLog::getStartDate, Collectors.counting()));

		List<ClientDashboardResponse.AttendanceDay> heatmap = new ArrayList<>();
		for (long i = 83; i >= 0; i--) {
			LocalDate date = today.minusDays(i);
			heatmap.add(new ClientDashboardResponse.AttendanceDay(date, countByDate.getOrDefault(date, 0L)));
		}

		return new ClientDashboardResponse.AttendanceInfo(totalSessions, sessionsThisMonth, streak, heatmap);
	}

	private MembershipRatingResponse toRatingResponse(MembershipRating r) {
		return new MembershipRatingResponse(
				r.getId(),
				r.getUserGymMembershipId(),
				r.getCoachId(),
				r.getDate(),
				r.getWeight(),
				r.getObservation(),
				r.getPorcentageFat(),
				r.getMuscleMass(),
				r.getStatus()
		);
	}

	private MembershipResultResponse toResultResponse(MembershipResult r) {
		return new MembershipResultResponse(
				r.getId(),
				r.getUserGymMembershipId(),
				r.getCoachId(),
				r.getStartWeight(),
				r.getEndWeight(),
				r.getStartFat(),
				r.getEndFat(),
				r.getStartMuscleMass(),
				r.getEndMuscleMass(),
				r.getCreatedAt(),
				r.getStatus()
		);
	}
}
