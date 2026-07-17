package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.SaasPlan;
import com.conovax.fitflow.infrastructure.persistence.entities.SaasPlanJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SaasPlanMapper {

	public SaasPlan toDomain(SaasPlanJpaEntity jpaEntity) {
		if (jpaEntity == null) {
			return null;
		}

		return SaasPlan.builder()
				.id(jpaEntity.getId())
				.code(jpaEntity.getCode())
				.name(jpaEntity.getName())
				.description(jpaEntity.getDescription())
				.price(jpaEntity.getPrice())
				.numDays(jpaEntity.getNumDays())
				.stripePriceId(jpaEntity.getStripePriceId())
				.status(jpaEntity.getStatus())
				.build();
	}

	public SaasPlanJpaEntity toJpaEntity(SaasPlan domain) {
		if (domain == null) {
			return null;
		}

		return SaasPlanJpaEntity.builder()
				.id(domain.getId())
				.code(domain.getCode())
				.name(domain.getName())
				.description(domain.getDescription())
				.price(domain.getPrice())
				.numDays(domain.getNumDays())
				.stripePriceId(domain.getStripePriceId())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}