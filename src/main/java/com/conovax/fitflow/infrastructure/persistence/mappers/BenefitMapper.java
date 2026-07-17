package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.Benefit;
import com.conovax.fitflow.infrastructure.persistence.entities.BenefitJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class BenefitMapper {

	public Benefit toDomain(BenefitJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return Benefit.builder()
				.id(jpaEntity.getId())
				.name(jpaEntity.getName())
				.description(jpaEntity.getDescription())
				.status(jpaEntity.getStatus())
				.gymId(jpaEntity.getGymId())
				.build();
	}

	public BenefitJpaEntity toJpaEntity(Benefit domain) {
		if (domain == null) return null;

		return BenefitJpaEntity.builder()
				.id(domain.getId())
				.name(domain.getName())
				.description(domain.getDescription())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.gymId(domain.getGymId())
				.build();
	}
}
