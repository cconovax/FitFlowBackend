package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.MembershipBenefit;

import java.util.Collection;
import java.util.List;

public interface MembershipBenefitRepository {
	void deleteByMembershipId(Long membershipId);

	List<MembershipBenefit> findAllByMembershipIdIn(Collection<Long> membershipIds);

	List<MembershipBenefit> saveAll(List<MembershipBenefit> entities);
}
