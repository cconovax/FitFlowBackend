package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.projections.UserGymMembershipActiveProjection;
import com.conovax.fitflow.infrastructure.persistence.entities.UserGymMembershipJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserGymMembershipJpaRepository extends JpaRepository<UserGymMembershipJpaEntity, Long> {
	List<UserGymMembershipJpaEntity> findAllByUserGymIdOrderByStartDateDescIdDesc(Long userGymId);

	boolean existsByUserGymIdAndStatusTrueAndEndDateGreaterThanEqual(Long userGymId, LocalDate today);

	boolean existsByUserGymIdAndMembershipIdAndStatusTrueAndEndDateGreaterThanEqual(
			Long userGymId,
			Long membershipId,
			LocalDate today
	);

	@org.springframework.data.jpa.repository.Query(
			nativeQuery = true,
			value = """
				SELECT
					ugm.user_gym_membership_id AS userGymMembershipId,
					ugm.user_gym_id AS userGymId,
					ugm.membership_id AS membershipId,
					ugm.start_date AS startDate,
					ugm.end_date AS endDate,
					ugm.status AS status,
					u.user_id AS userId,
					p.people_id AS peopleId,
					p.names AS names,
					p.surnames AS surnames,
					p.email AS email,
					p.phone AS phone
				FROM user_gym_membership ugm
				JOIN users_gyms ug ON ug.user_gym_id = ugm.user_gym_id
				JOIN users u ON u.user_id = ug.user_id
				JOIN peoples p ON p.people_id = u.people_id
				WHERE ugm.membership_id = :membershipId
					AND ugm.status = true
					AND ugm.end_date >= :today
				ORDER BY ugm.end_date DESC, ugm.user_gym_membership_id DESC
			"""
	)
	java.util.List<UserGymMembershipActiveProjection> findActiveUsersByMembershipId(
			@org.springframework.data.repository.query.Param("membershipId") Long membershipId,
			@org.springframework.data.repository.query.Param("today") LocalDate today
	);

	@Query(nativeQuery = true, value = """
		SELECT COUNT(*)
		FROM user_gym_membership ugm
		JOIN users_gyms ug ON ug.user_gym_id = ugm.user_gym_id
		WHERE ug.gym_id = :gymId
		  AND ugm.status = true
		  AND ugm.end_date >= :today
		""")
	long countActiveMembershipsByGymId(@Param("gymId") Long gymId, @Param("today") LocalDate today);

	@Query(nativeQuery = true, value = """
		SELECT COUNT(*)
		FROM user_gym_membership ugm
		JOIN users_gyms ug ON ug.user_gym_id = ugm.user_gym_id
		WHERE ug.gym_id = :gymId
		  AND ugm.start_date >= :from
		  AND ugm.start_date <= :to
		""")
	long countMembershipsByGymIdAndDateRange(
			@Param("gymId") Long gymId,
			@Param("from") LocalDate from,
			@Param("to") LocalDate to
	);

	@Query(nativeQuery = true, value = """
		SELECT m.name AS membershipName, COUNT(*) AS total,
		       SUM(CASE WHEN ugm.status = true AND ugm.end_date >= :today THEN 1 ELSE 0 END) AS activeCount
		FROM user_gym_membership ugm
		JOIN users_gyms ug ON ug.user_gym_id = ugm.user_gym_id
		JOIN memberships m ON m.membership_id = ugm.membership_id
		WHERE ug.gym_id = :gymId
		GROUP BY m.membership_id, m.name
		ORDER BY total DESC
		LIMIT 10
		""")
	List<Object[]> findMembershipDistributionByGymId(
			@Param("gymId") Long gymId,
			@Param("today") LocalDate today
	);
}
