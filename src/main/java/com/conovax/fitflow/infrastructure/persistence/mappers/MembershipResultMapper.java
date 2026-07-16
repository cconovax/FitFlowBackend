package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.MembershipResult;
import com.conovax.sexbody.infrastructure.persistence.entities.MembershipResultJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MembershipResultMapper {

	public MembershipResult toDomain(MembershipResultJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return MembershipResult.builder()
				.id(jpaEntity.getId())
				.userGymMembershipId(jpaEntity.getUserGymMembershipId())
				.coachId(jpaEntity.getCoachId())
				.startWeight(jpaEntity.getStartWeight())
				.endWeight(jpaEntity.getEndWeight())
				.startFat(jpaEntity.getStartFat())
				.endFat(jpaEntity.getEndFat())
				.startMuscleMass(jpaEntity.getStartMuscleMass())
				.endMuscleMass(jpaEntity.getEndMuscleMass())
				.createdAt(jpaEntity.getCreatedAt())
				.status(jpaEntity.getStatus())
				.build();
	}

	public MembershipResultJpaEntity toJpaEntity(MembershipResult domain) {
		if (domain == null) return null;

		return MembershipResultJpaEntity.builder()
				.id(domain.getId())
				.userGymMembershipId(domain.getUserGymMembershipId())
				.coachId(domain.getCoachId())
				.startWeight(domain.getStartWeight())
				.endWeight(domain.getEndWeight())
				.startFat(domain.getStartFat())
				.endFat(domain.getEndFat())
				.startMuscleMass(domain.getStartMuscleMass())
				.endMuscleMass(domain.getEndMuscleMass())
				.createdAt(domain.getCreatedAt())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
