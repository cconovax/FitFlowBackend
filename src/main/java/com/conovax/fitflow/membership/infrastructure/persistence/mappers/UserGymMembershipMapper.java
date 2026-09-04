package com.conovax.fitflow.membership.infrastructure.persistence.mappers;

import com.conovax.fitflow.membership.domain.entities.UserGymMembership;
import com.conovax.fitflow.membership.infrastructure.persistence.entities.UserGymMembershipJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserGymMembershipMapper {

	public UserGymMembership toDomain(UserGymMembershipJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return UserGymMembership.builder()
				.id(jpaEntity.getId())
				.userGymId(jpaEntity.getUserGymId())
				.membershipId(jpaEntity.getMembershipId())
				.paymentPreci(jpaEntity.getPaymentPreci())
				.startDate(jpaEntity.getStartDate())
				.endDate(jpaEntity.getEndDate())
				.status(jpaEntity.getStatus())
				.build();
	}

	public UserGymMembershipJpaEntity toJpaEntity(UserGymMembership domain) {
		if (domain == null) return null;

		return UserGymMembershipJpaEntity.builder()
				.id(domain.getId())
				.userGymId(domain.getUserGymId())
				.membershipId(domain.getMembershipId())
				.paymentPreci(domain.getPaymentPreci())
				.startDate(domain.getStartDate())
				.endDate(domain.getEndDate())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
