package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.Membership;
import com.conovax.sexbody.infrastructure.persistence.entities.MembershipJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MembershipMapper {

	public Membership toDomain(MembershipJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return Membership.builder()
				.id(jpaEntity.getId())
				.name(jpaEntity.getName())
				.description(jpaEntity.getDescription())
				.durationDay(jpaEntity.getDurationDay())
				.status(jpaEntity.getStatus())
				.gymId(jpaEntity.getGymId())
				.build();
	}

	public MembershipJpaEntity toJpaEntity(Membership domain) {
		if (domain == null) return null;

		return MembershipJpaEntity.builder()
				.id(domain.getId())
				.name(domain.getName())
				.description(domain.getDescription())
				.durationDay(domain.getDurationDay())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.gymId(domain.getGymId())
				.build();
	}
}
