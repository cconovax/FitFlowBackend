package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.GymSubscription;
import com.conovax.fitflow.domain.repositories.GymSubscriptionRepository;
import com.conovax.fitflow.infrastructure.persistence.mappers.GymSubscriptionMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.GymSubscriptionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GymSubscriptionRepositoryAdapter implements GymSubscriptionRepository {

	private final GymSubscriptionJpaRepository jpaRepository;
	private final GymSubscriptionMapper mapper;

	@Override
	public GymSubscription save(GymSubscription subscription) {
		return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(subscription)));
	}

	@Override
	public java.util.Optional<GymSubscription> findById(Long id) {
		return jpaRepository.findById(id)
				.map(mapper::toDomain);
	}

	@Override
	public List<GymSubscription> findAllByGymIdOrderByEndDateDescIdDesc(Long gymId) {
		return jpaRepository.findAllByGym_IdOrderByEndDateDescIdDesc(gymId).stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public boolean existsByGymIdAndSaasPlanId(Long gymId, Long saasPlanId) {
		return jpaRepository.existsByGym_IdAndSaasPlanId(gymId, saasPlanId);
	}
}