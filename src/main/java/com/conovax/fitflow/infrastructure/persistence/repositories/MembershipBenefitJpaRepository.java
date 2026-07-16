package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.MembershipBenefitJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MembershipBenefitJpaRepository extends JpaRepository<MembershipBenefitJpaEntity, Long> {
	void deleteByMembershipId(Long membershipId);

	List<MembershipBenefitJpaEntity> findAllByMembershipIdIn(Collection<Long> membershipIds);
}
