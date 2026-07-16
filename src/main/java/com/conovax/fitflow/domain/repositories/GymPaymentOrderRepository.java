package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.GymPaymentOrder;

import java.util.Optional;

public interface GymPaymentOrderRepository {
	GymPaymentOrder save(GymPaymentOrder order);

	Optional<GymPaymentOrder> findById(Long id);

	Optional<GymPaymentOrder> findByStripeSubscriptionId(String stripeSubscriptionId);

	Optional<GymPaymentOrder> findByStripeCustomerId(String stripeCustomerId);

	Optional<GymPaymentOrder> findLatestActiveByGymId(Long gymId);

	Optional<GymPaymentOrder> findLatestOrderByGymId(Long gymId);
}
