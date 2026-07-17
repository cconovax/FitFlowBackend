package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.MembershipCoach;

import java.util.List;
import java.util.Optional;

public interface MembershipCoachRepository {
	MembershipCoach save(MembershipCoach entity);

	Optional<MembershipCoach> findByIdAndStatusTrue(Long id);

	Optional<MembershipCoach> findByIdAndStatusFalse(Long id);

	List<MembershipCoach> findAllByGymId(Long gymId);

	List<MembershipCoach> findAllByCoachId(Long coachId);

	boolean existsByMembershipIdAndCoachIdAndStatusTrue(Long membershipId, Long coachId);

	boolean existsById(Long id);

	void deleteById(Long id);
}
