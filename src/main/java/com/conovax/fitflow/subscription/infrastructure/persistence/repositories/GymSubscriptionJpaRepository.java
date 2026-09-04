package com.conovax.fitflow.subscription.infrastructure.persistence.repositories;

import com.conovax.fitflow.subscription.infrastructure.persistence.entities.GymSubscriptionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GymSubscriptionJpaRepository extends JpaRepository<GymSubscriptionJpaEntity, Long> {
	List<GymSubscriptionJpaEntity> findAllByGym_IdOrderByEndDateDescIdDesc(Long gymId);

	boolean existsByGym_IdAndSaasPlanId(Long gymId, Long saasPlanId);
}