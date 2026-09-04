package com.conovax.fitflow.membership.domain.repositories;

import com.conovax.fitflow.membership.domain.entities.MembershipResult;

import java.util.Optional;

public interface MembershipResultRepository {
	MembershipResult save(MembershipResult entity);

	Optional<MembershipResult> findByUserGymMembershipId(Long userGymMembershipId);

	boolean existsByUserGymMembershipId(Long userGymMembershipId);
}
