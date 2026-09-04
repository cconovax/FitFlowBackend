package com.conovax.fitflow.gym.infrastructure.persistence.adapters;

import com.conovax.fitflow.gym.domain.entities.SessionLog;
import com.conovax.fitflow.gym.domain.repositories.SessionLogRepository;
import com.conovax.fitflow.gym.infrastructure.persistence.repositories.SessionLogJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SessionLogRepositoryAdapter implements SessionLogRepository {

	private final SessionLogJpaRepository jpaRepository;

	@Override
	public List<SessionLog> findAllByUserGymIdOrderByStartDateDesc(Long userGymId) {
		return jpaRepository.findAllByUserGymIdOrderByStartDateDesc(userGymId)
				.stream()
				.map(e -> SessionLog.builder()
						.id(e.getId())
						.userGymId(e.getUserGymId())
						.startDate(e.getStartDate())
						.endDate(e.getEndDate())
						.build())
				.toList();
	}

	@Override
	public long countSessionsTodayByGymId(Long gymId, LocalDate today) {
		return jpaRepository.countSessionsTodayByGymId(gymId, today);
	}

	@Override
	public long countSessionsByGymIdAndDateRange(Long gymId, LocalDate from, LocalDate to) {
		return jpaRepository.countSessionsByGymIdAndDateRange(gymId, from, to);
	}

	@Override
	public List<Object[]> findDailySessionsByGymId(Long gymId, LocalDate from, LocalDate to) {
		return jpaRepository.findDailySessionsByGymId(gymId, from, to);
	}
}
