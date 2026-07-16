package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.GymSubscription;

import java.util.List;
import java.util.Optional;

public interface GymSubscriptionRepository {
	GymSubscription save(GymSubscription subscription);

	Optional<GymSubscription> findById(Long id);

	List<GymSubscription> findAllByGymIdOrderByEndDateDescIdDesc(Long gymId);

	boolean existsByGymIdAndSaasPlanId(Long gymId, Long saasPlanId);
}