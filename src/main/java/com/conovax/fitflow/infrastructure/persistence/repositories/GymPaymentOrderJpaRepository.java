package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.GymPaymentOrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GymPaymentOrderJpaRepository extends JpaRepository<GymPaymentOrderJpaEntity, Long> {

	Optional<GymPaymentOrderJpaEntity> findByStripeSubscriptionId(String stripeSubscriptionId);

	Optional<GymPaymentOrderJpaEntity> findByStripeCustomerId(String stripeCustomerId);

	@Query("SELECT o FROM GymPaymentOrderJpaEntity o WHERE o.gym.id = :gymId AND o.status = 'ACTIVE' ORDER BY o.id DESC LIMIT 1")
	Optional<GymPaymentOrderJpaEntity> findLatestActiveByGymId(@Param("gymId") Long gymId);

	@Query("SELECT o FROM GymPaymentOrderJpaEntity o WHERE o.gym.id = :gymId ORDER BY o.id DESC LIMIT 1")
	Optional<GymPaymentOrderJpaEntity> findLatestOrderByGymId(@Param("gymId") Long gymId);
}
