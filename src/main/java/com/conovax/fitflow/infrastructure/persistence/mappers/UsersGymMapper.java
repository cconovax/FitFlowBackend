package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.UsersGym;
import com.conovax.fitflow.infrastructure.persistence.entities.UsersGymJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UsersGymMapper {

	public UsersGym toDomain(UsersGymJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return UsersGym.builder()
				.id(jpaEntity.getId())
				.userId(jpaEntity.getUserId())
				.gymId(jpaEntity.getGymId())
				.fingerprint(jpaEntity.getFingerprint())
				.status(jpaEntity.getStatus())
				.build();
	}

	public UsersGymJpaEntity toJpaEntity(UsersGym domain) {
		if (domain == null) return null;

		return UsersGymJpaEntity.builder()
				.id(domain.getId())
				.userId(domain.getUserId())
				.gymId(domain.getGymId())
				.fingerprint(domain.getFingerprint())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
