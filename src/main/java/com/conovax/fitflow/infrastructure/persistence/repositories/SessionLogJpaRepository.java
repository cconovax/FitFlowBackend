package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.SessionLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SessionLogJpaRepository extends JpaRepository<SessionLogJpaEntity, Long> {
	List<SessionLogJpaEntity> findAllByUserGymIdOrderByStartDateDesc(Long userGymId);

	@Query(nativeQuery = true, value = """
		SELECT COUNT(*)
		FROM session_logs sl
		JOIN users_gyms ug ON ug.user_gym_id = sl.user_gym_id
		WHERE ug.gym_id = :gymId
		  AND sl.start_date = :today
		""")
	long countSessionsTodayByGymId(@Param("gymId") Long gymId, @Param("today") LocalDate today);

	@Query(nativeQuery = true, value = """
		SELECT COUNT(*)
		FROM session_logs sl
		JOIN users_gyms ug ON ug.user_gym_id = sl.user_gym_id
		WHERE ug.gym_id = :gymId
		  AND sl.start_date >= :from
		  AND sl.start_date <= :to
		""")
	long countSessionsByGymIdAndDateRange(
			@Param("gymId") Long gymId,
			@Param("from") LocalDate from,
			@Param("to") LocalDate to
	);

	@Query(nativeQuery = true, value = """
		SELECT sl.start_date AS day, COUNT(*) AS cnt
		FROM session_logs sl
		JOIN users_gyms ug ON ug.user_gym_id = sl.user_gym_id
		WHERE ug.gym_id = :gymId
		  AND sl.start_date >= :from
		  AND sl.start_date <= :to
		GROUP BY sl.start_date
		ORDER BY sl.start_date
		""")
	List<Object[]> findDailySessionsByGymId(
			@Param("gymId") Long gymId,
			@Param("from") LocalDate from,
			@Param("to") LocalDate to
	);
}
