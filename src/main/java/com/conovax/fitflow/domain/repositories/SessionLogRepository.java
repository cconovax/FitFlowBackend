package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.SessionLog;

import java.time.LocalDate;
import java.util.List;

public interface SessionLogRepository {
	List<SessionLog> findAllByUserGymIdOrderByStartDateDesc(Long userGymId);

	long countSessionsTodayByGymId(Long gymId, LocalDate today);

	long countSessionsByGymIdAndDateRange(Long gymId, LocalDate from, LocalDate to);

	List<Object[]> findDailySessionsByGymId(Long gymId, LocalDate from, LocalDate to);
}
