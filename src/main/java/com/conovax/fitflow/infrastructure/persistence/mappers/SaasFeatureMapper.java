package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.SaasFeature;
import com.conovax.sexbody.infrastructure.persistence.entities.SaasFeatureJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SaasFeatureMapper {

	public SaasFeature toDomain(SaasFeatureJpaEntity jpaEntity) {
		if (jpaEntity == null) {
			return null;
		}

		return SaasFeature.builder()
				.id(jpaEntity.getId())
				.code(jpaEntity.getCode())
				.name(jpaEntity.getName())
				.description(jpaEntity.getDescription())
				.featureType(jpaEntity.getFeatureType())
				.status(jpaEntity.getStatus())
				.build();
	}

	public SaasFeatureJpaEntity toJpaEntity(SaasFeature domain) {
		if (domain == null) {
			return null;
		}

		return SaasFeatureJpaEntity.builder()
				.id(domain.getId())
				.code(domain.getCode())
				.name(domain.getName())
				.description(domain.getDescription())
				.featureType(domain.getFeatureType())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}