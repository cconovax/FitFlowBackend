package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.MembershipPrice;

import java.util.Collection;
import java.util.List;

public interface MembershipPriceRepository {
	List<MembershipPrice> findAllByMembershipIdAndEndDateIsNull(Long membershipId);

	List<MembershipPrice> findAllByMembershipIdInAndEndDateIsNull(Collection<Long> membershipIds);

	List<MembershipPrice> saveAll(List<MembershipPrice> entities);

	MembershipPrice save(MembershipPrice entity);
}
