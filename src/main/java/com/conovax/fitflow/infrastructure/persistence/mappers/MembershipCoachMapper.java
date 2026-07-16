package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.MembershipCoach;
import com.conovax.sexbody.infrastructure.persistence.entities.MembershipCoachJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MembershipCoachMapper {

	public MembershipCoach toDomain(MembershipCoachJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return MembershipCoach.builder()
				.id(jpaEntity.getId())
				.membershipId(jpaEntity.getMembershipId())
				.coachId(jpaEntity.getCoachId())
				.status(jpaEntity.getStatus())
				.build();
	}

	public MembershipCoachJpaEntity toJpaEntity(MembershipCoach domain) {
		if (domain == null) return null;

		return MembershipCoachJpaEntity.builder()
				.id(domain.getId())
				.membershipId(domain.getMembershipId())
				.coachId(domain.getCoachId())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
