package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.GymPaymentOrder;

import java.util.Optional;

public interface GymPaymentOrderRepository {
	GymPaymentOrder save(GymPaymentOrder order);

	Optional<GymPaymentOrder> findById(Long id);

	Optional<GymPaymentOrder> findByStripeSubscriptionId(String stripeSubscriptionId);

	Optional<GymPaymentOrder> findByStripeCustomerId(String stripeCustomerId);

	Optional<GymPaymentOrder> findLatestActiveByGymId(Long gymId);

	Optional<GymPaymentOrder> findLatestOrderByGymId(Long gymId);
}
