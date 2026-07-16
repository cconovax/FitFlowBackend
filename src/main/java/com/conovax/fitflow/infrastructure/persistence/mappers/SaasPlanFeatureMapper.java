package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.SaasPlanFeature;
import com.conovax.sexbody.infrastructure.persistence.entities.SaasFeatureJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.entities.SaasPlanFeatureJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.entities.SaasPlanJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SaasPlanFeatureMapper {

	public SaasPlanFeature toDomain(SaasPlanFeatureJpaEntity jpaEntity) {
		if (jpaEntity == null) {
			return null;
		}

		return SaasPlanFeature.builder()
				.id(jpaEntity.getId())
				.saasPlanId(jpaEntity.getSaasPlan() != null ? jpaEntity.getSaasPlan().getId() : null)
				.saasFeatureId(jpaEntity.getSaasFeature() != null ? jpaEntity.getSaasFeature().getId() : null)
				.valueText(jpaEntity.getValueText())
				.valueNumber(jpaEntity.getValueNumber())
				.valueBoolean(jpaEntity.getValueBoolean())
				.build();
	}

	public SaasPlanFeatureJpaEntity toJpaEntity(SaasPlanFeature domain) {
		if (domain == null) {
			return null;
		}

		SaasPlanJpaEntity planRef = SaasPlanJpaEntity.builder().id(domain.getSaasPlanId()).build();
		SaasFeatureJpaEntity featureRef = SaasFeatureJpaEntity.builder().id(domain.getSaasFeatureId()).build();

		return SaasPlanFeatureJpaEntity.builder()
				.id(domain.getId())
				.saasPlan(planRef)
				.saasFeature(featureRef)
				.valueText(domain.getValueText())
				.valueNumber(domain.getValueNumber())
				.valueBoolean(domain.getValueBoolean())
				.build();
	}
}