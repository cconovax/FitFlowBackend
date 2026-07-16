package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.MembershipCoachJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipCoachJpaRepository extends JpaRepository<MembershipCoachJpaEntity, Long> {
	Optional<MembershipCoachJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<MembershipCoachJpaEntity> findByIdAndStatusFalse(Long id);

	List<MembershipCoachJpaEntity> findAllByCoachId(Long coachId);

	boolean existsByMembershipIdAndCoachIdAndStatusTrue(Long membershipId, Long coachId);

	@Query(
			nativeQuery = true,
			value = """
				SELECT mc.*
				FROM membership_coachs mc
				JOIN memberships m ON m.membership_id = mc.membership_id
				WHERE m.gym_id = :gymId OR m.gym_id IS NULL
				ORDER BY mc.membership_coach_id DESC
			"""
	)
	List<MembershipCoachJpaEntity> findAllByGymId(@Param("gymId") Long gymId);
}
