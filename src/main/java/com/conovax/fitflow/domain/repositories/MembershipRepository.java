package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.Membership;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository {
	Membership save(Membership membership);

	List<Membership> findAllByStatusTrue();

	List<Membership> findAllByGymIdAndStatusTrue(Long gymId);

	List<Membership> findAllByGymIdOrGymIdIsNullAndStatusTrue(Long gymId);

	Optional<Membership> findByIdAndStatusTrue(Long id);

	Optional<Membership> findByIdAndStatusFalse(Long id);

	boolean existsByNameIgnoreCaseAndGymIdAndStatusTrue(String name, Long gymId);

	boolean existsByNameIgnoreCaseAndGymIdAndIdNotAndStatusTrue(String name, Long gymId, Long id);

	List<Membership> findAllById(Iterable<Long> ids);

	boolean existsById(Long id);

	void deleteById(Long id);
}
