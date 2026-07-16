package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.MembershipRating;

import java.util.List;
import java.util.Optional;

public interface MembershipRatingRepository {
	MembershipRating save(MembershipRating entity);

	List<MembershipRating> findAllByUserGymMembershipIdOrderByDateDescIdDesc(Long userGymMembershipId);

	Optional<MembershipRating> findOldestByUserGymMembershipId(Long userGymMembershipId);

	Optional<MembershipRating> findById(Long id);

	boolean existsById(Long id);

	void deleteById(Long id);
}
