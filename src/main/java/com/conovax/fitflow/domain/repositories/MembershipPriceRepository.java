package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.MembershipPrice;

import java.util.Collection;
import java.util.List;

public interface MembershipPriceRepository {
	List<MembershipPrice> findAllByMembershipIdAndEndDateIsNull(Long membershipId);

	List<MembershipPrice> findAllByMembershipIdInAndEndDateIsNull(Collection<Long> membershipIds);

	List<MembershipPrice> saveAll(List<MembershipPrice> entities);

	MembershipPrice save(MembershipPrice entity);
}
