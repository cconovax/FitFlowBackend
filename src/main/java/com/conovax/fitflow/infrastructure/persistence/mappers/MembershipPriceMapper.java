package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.MembershipPrice;
import com.conovax.fitflow.infrastructure.persistence.entities.MembershipPriceJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MembershipPriceMapper {

	public MembershipPrice toDomain(MembershipPriceJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return MembershipPrice.builder()
				.id(jpaEntity.getId())
				.membershipId(jpaEntity.getMembershipId())
				.price(jpaEntity.getPrice())
				.startDate(jpaEntity.getStartDate())
				.endDate(jpaEntity.getEndDate())
				.build();
	}

	public MembershipPriceJpaEntity toJpaEntity(MembershipPrice domain) {
		if (domain == null) return null;

		return MembershipPriceJpaEntity.builder()
				.id(domain.getId())
				.membershipId(domain.getMembershipId())
				.price(domain.getPrice())
				.startDate(domain.getStartDate())
				.endDate(domain.getEndDate())
				.build();
	}
}
