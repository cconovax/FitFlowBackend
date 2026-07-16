package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.SessionLog;
import com.conovax.sexbody.domain.repositories.SessionLogRepository;
import com.conovax.sexbody.infrastructure.persistence.repositories.SessionLogJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
