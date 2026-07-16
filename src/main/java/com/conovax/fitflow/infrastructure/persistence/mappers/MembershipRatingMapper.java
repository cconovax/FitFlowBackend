package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.MembershipRating;
import com.conovax.sexbody.infrastructure.persistence.entities.MembershipRatingJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MembershipRatingMapper {

	public MembershipRating toDomain(MembershipRatingJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return MembershipRating.builder()
				.id(jpaEntity.getId())
				.userGymMembershipId(jpaEntity.getUserGymMembershipId())
				.coachId(jpaEntity.getCoachId())
				.date(jpaEntity.getDate())
				.weight(jpaEntity.getWeight())
				.observation(jpaEntity.getObservation())
				.porcentageFat(jpaEntity.getPorcentageFat())
				.muscleMass(jpaEntity.getMuscleMass())
				.status(jpaEntity.getStatus())
				.build();
	}

	public MembershipRatingJpaEntity toJpaEntity(MembershipRating domain) {
		if (domain == null) return null;

		return MembershipRatingJpaEntity.builder()
				.id(domain.getId())
				.userGymMembershipId(domain.getUserGymMembershipId())
				.coachId(domain.getCoachId())
				.date(domain.getDate())
				.weight(domain.getWeight())
				.observation(domain.getObservation())
				.porcentageFat(domain.getPorcentageFat())
				.muscleMass(domain.getMuscleMass())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
