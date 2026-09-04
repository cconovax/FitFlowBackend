package com.conovax.fitflow.membership.infrastructure.persistence.mappers;

import com.conovax.fitflow.membership.domain.entities.MembershipBenefit;
import com.conovax.fitflow.membership.infrastructure.persistence.entities.MembershipBenefitJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MembershipBenefitMapper {

	public MembershipBenefit toDomain(MembershipBenefitJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return MembershipBenefit.builder()
				.id(jpaEntity.getId())
				.membershipId(jpaEntity.getMembershipId())
				.benefitId(jpaEntity.getBenefitId())
				.build();
	}

	public MembershipBenefitJpaEntity toJpaEntity(MembershipBenefit domain) {
		if (domain == null) return null;

		return MembershipBenefitJpaEntity.builder()
				.id(domain.getId())
				.membershipId(domain.getMembershipId())
				.benefitId(domain.getBenefitId())
				.build();
	}
}
