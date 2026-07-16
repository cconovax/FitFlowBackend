package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.MembershipResult;

import java.util.Optional;

public interface MembershipResultRepository {
	MembershipResult save(MembershipResult entity);

	Optional<MembershipResult> findByUserGymMembershipId(Long userGymMembershipId);

	boolean existsByUserGymMembershipId(Long userGymMembershipId);
}
