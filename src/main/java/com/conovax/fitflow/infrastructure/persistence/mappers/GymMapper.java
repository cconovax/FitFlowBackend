package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.Gym;
import com.conovax.fitflow.domain.entities.GymStatus;
import com.conovax.fitflow.infrastructure.persistence.entities.GymJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.repositories.GymJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class GymMapper {

	private final GymJpaRepository gymJpaRepository;

	public GymMapper(GymJpaRepository gymJpaRepository) {
		this.gymJpaRepository = gymJpaRepository;
	}

	public Gym toDomain(GymJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return Gym.builder()
				.id(jpaEntity.getId())
				.name(jpaEntity.getName())
				.nit(jpaEntity.getNit())
				.logo(jpaEntity.getLogo())
				.municipalitieId(jpaEntity.getMunicipalitieId())
				.status(jpaEntity.getStatus())
				.phone(jpaEntity.getPhone())
				.email(jpaEntity.getEmail())
				.parentGymId(jpaEntity.getParentGym() != null ? jpaEntity.getParentGym().getId() : null)
				.gymStatus(jpaEntity.getGymStatus() != null ? jpaEntity.getGymStatus() : GymStatus.ACTIVE)
				.trialExpiresAt(jpaEntity.getTrialExpiresAt())
				.build();
	}

	public GymJpaEntity toJpaEntity(Gym domain) {
		if (domain == null) return null;

		GymJpaEntity parentGym = null;
		if (domain.getParentGymId() != null) {
			parentGym = gymJpaRepository.findById(domain.getParentGymId()).orElse(null);
		}

		return GymJpaEntity.builder()
				.id(domain.getId())
				.name(domain.getName())
				.nit(domain.getNit())
				.logo(domain.getLogo())
				.municipalitieId(domain.getMunicipalitieId())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.phone(domain.getPhone())
				.email(domain.getEmail())
				.parentGym(parentGym)
				.gymStatus(domain.getGymStatus() != null ? domain.getGymStatus() : GymStatus.ACTIVE)
				.trialExpiresAt(domain.getTrialExpiresAt())
				.build();
	}
}
