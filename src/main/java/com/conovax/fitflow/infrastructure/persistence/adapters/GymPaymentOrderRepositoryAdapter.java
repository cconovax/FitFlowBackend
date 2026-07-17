package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.GymPaymentOrder;
import com.conovax.fitflow.domain.repositories.GymPaymentOrderRepository;
import com.conovax.fitflow.infrastructure.persistence.mappers.GymPaymentOrderMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.GymPaymentOrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GymPaymentOrderRepositoryAdapter implements GymPaymentOrderRepository {

	private final GymPaymentOrderJpaRepository jpaRepository;
	private final GymPaymentOrderMapper mapper;

	@Override
	public GymPaymentOrder save(GymPaymentOrder order) {
		return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(order)));
	}

	@Override
	public Optional<GymPaymentOrder> findById(Long id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public Optional<GymPaymentOrder> findByStripeSubscriptionId(String stripeSubscriptionId) {
		return jpaRepository.findByStripeSubscriptionId(stripeSubscriptionId).map(mapper::toDomain);
	}

	@Override
	public Optional<GymPaymentOrder> findByStripeCustomerId(String stripeCustomerId) {
		return jpaRepository.findByStripeCustomerId(stripeCustomerId).map(mapper::toDomain);
	}

	@Override
	public Optional<GymPaymentOrder> findLatestActiveByGymId(Long gymId) {
		return jpaRepository.findLatestActiveByGymId(gymId).map(mapper::toDomain);
	}

	@Override
	public Optional<GymPaymentOrder> findLatestOrderByGymId(Long gymId) {
		return jpaRepository.findLatestOrderByGymId(gymId).map(mapper::toDomain);
	}
}
