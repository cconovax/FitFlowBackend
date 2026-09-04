package com.conovax.fitflow.membership.domain.repositories;

import com.conovax.fitflow.membership.domain.entities.MembershipBenefit;

import java.util.Collection;
import java.util.List;

public interface MembershipBenefitRepository {
	void deleteByMembershipId(Long membershipId);

	List<MembershipBenefit> findAllByMembershipIdIn(Collection<Long> membershipIds);

	List<MembershipBenefit> saveAll(List<MembershipBenefit> entities);
}
