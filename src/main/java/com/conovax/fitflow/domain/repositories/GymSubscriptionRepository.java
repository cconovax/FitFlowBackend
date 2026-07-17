package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.GymSubscription;

import java.util.List;
import java.util.Optional;

public interface GymSubscriptionRepository {
	GymSubscription save(GymSubscription subscription);

	Optional<GymSubscription> findById(Long id);

	List<GymSubscription> findAllByGymIdOrderByEndDateDescIdDesc(Long gymId);

	boolean existsByGymIdAndSaasPlanId(Long gymId, Long saasPlanId);
}