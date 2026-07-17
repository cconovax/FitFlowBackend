package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.MembershipResult;

import java.util.Optional;

public interface MembershipResultRepository {
	MembershipResult save(MembershipResult entity);

	Optional<MembershipResult> findByUserGymMembershipId(Long userGymMembershipId);

	boolean existsByUserGymMembershipId(Long userGymMembershipId);
}
