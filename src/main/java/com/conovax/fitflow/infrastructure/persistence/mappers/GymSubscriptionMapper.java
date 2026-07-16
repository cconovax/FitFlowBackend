package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.GymSubscription;
import com.conovax.sexbody.infrastructure.persistence.entities.GymJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.entities.GymSubscriptionJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class GymSubscriptionMapper {

	public GymSubscription toDomain(GymSubscriptionJpaEntity jpaEntity) {
		if (jpaEntity == null) {
			return null;
		}

		Long gymId = jpaEntity.getGym() != null ? jpaEntity.getGym().getId() : null;

		return GymSubscription.builder()
				.id(jpaEntity.getId())
				.gymId(gymId)
				.saasPlanId(jpaEntity.getSaasPlanId())
				.startDate(jpaEntity.getStartDate())
				.endDate(jpaEntity.getEndDate())
				.status(jpaEntity.getStatus())
				.notes(jpaEntity.getNotes())
				.createdAt(jpaEntity.getCreatedAt())
				.updatedAt(jpaEntity.getUpdatedAt())
				.build();
	}

	public GymSubscriptionJpaEntity toJpaEntity(GymSubscription domain) {
		if (domain == null) {
			return null;
		}

		GymJpaEntity gymRef = domain.getGymId() != null
				? GymJpaEntity.builder().id(domain.getGymId()).build()
				: null;

		return GymSubscriptionJpaEntity.builder()
				.id(domain.getId())
				.gym(gymRef)
				.saasPlanId(domain.getSaasPlanId())
				.startDate(domain.getStartDate())
				.endDate(domain.getEndDate())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.notes(domain.getNotes())
				.createdAt(domain.getCreatedAt())
				.updatedAt(domain.getUpdatedAt())
				.build();
	}
}