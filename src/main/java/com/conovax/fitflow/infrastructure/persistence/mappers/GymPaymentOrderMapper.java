package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.GymPaymentOrder;
import com.conovax.sexbody.infrastructure.persistence.entities.GymJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.entities.GymPaymentOrderJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class GymPaymentOrderMapper {

	public GymPaymentOrder toDomain(GymPaymentOrderJpaEntity jpaEntity) {
		if (jpaEntity == null) {
			return null;
		}
		Long gymId = jpaEntity.getGym() != null ? jpaEntity.getGym().getId() : null;
		return GymPaymentOrder.builder()
				.id(jpaEntity.getId())
				.gymId(gymId)
				.saasPlanId(jpaEntity.getSaasPlanId())
				.stripeSubscriptionId(jpaEntity.getStripeSubscriptionId())
				.stripeCustomerId(jpaEntity.getStripeCustomerId())
				.paymentGateway(jpaEntity.getPaymentGateway())
				.paymentMethodType(jpaEntity.getPaymentMethodType())
				.amountInCents(jpaEntity.getAmountInCents())
				.currency(jpaEntity.getCurrency())
				.status(jpaEntity.getStatus())
				.customerEmail(jpaEntity.getCustomerEmail())
				.createdAt(jpaEntity.getCreatedAt())
				.updatedAt(jpaEntity.getUpdatedAt())
				.build();
	}

	public GymPaymentOrderJpaEntity toJpaEntity(GymPaymentOrder domain) {
		if (domain == null) {
			return null;
		}
		GymJpaEntity gymRef = domain.getGymId() != null
				? GymJpaEntity.builder().id(domain.getGymId()).build()
				: null;
		return GymPaymentOrderJpaEntity.builder()
				.id(domain.getId())
				.gym(gymRef)
				.saasPlanId(domain.getSaasPlanId())
				.stripeSubscriptionId(domain.getStripeSubscriptionId())
				.stripeCustomerId(domain.getStripeCustomerId())
				.paymentGateway(domain.getPaymentGateway() != null ? domain.getPaymentGateway() : "STRIPE")
				.paymentMethodType(domain.getPaymentMethodType() != null ? domain.getPaymentMethodType() : "CARD")
				.amountInCents(domain.getAmountInCents())
				.currency(domain.getCurrency() != null ? domain.getCurrency() : "COP")
				.status(domain.getStatus() != null ? domain.getStatus() : "PENDING")
				.customerEmail(domain.getCustomerEmail())
				.createdAt(domain.getCreatedAt())
				.updatedAt(domain.getUpdatedAt())
				.build();
	}
}
