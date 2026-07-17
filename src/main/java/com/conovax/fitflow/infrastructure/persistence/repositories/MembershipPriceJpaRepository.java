package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.MembershipPriceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MembershipPriceJpaRepository extends JpaRepository<MembershipPriceJpaEntity, Long> {
	List<MembershipPriceJpaEntity> findAllByMembershipIdAndEndDateIsNull(Long membershipId);

	List<MembershipPriceJpaEntity> findAllByMembershipIdInAndEndDateIsNull(Collection<Long> membershipIds);
}
