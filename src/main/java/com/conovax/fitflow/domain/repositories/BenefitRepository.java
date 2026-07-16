package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.Benefit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BenefitRepository {
	Benefit save(Benefit benefit);

	List<Benefit> findAllByStatusTrue();

	List<Benefit> findAllByIdInAndStatusTrue(List<Long> ids);

	List<Benefit> findAllActiveByGymIdOrGlobal(Long gymId);

	Page<Benefit> findAllActiveByGymIdOrGlobalAndSearchFilter(Long gymId, String search, Pageable pageable);

	Optional<Benefit> findByIdAndStatusTrue(Long id);

	Optional<Benefit> findByIdAndStatusFalse(Long id);

	boolean existsByNameIgnoreCaseAndGymIdAndStatusTrue(String name, Long gymId);

	boolean existsByNameIgnoreCaseAndGymIdAndIdNotAndStatusTrue(String name, Long gymId, Long id);

	boolean existsById(Long id);

	void deleteById(Long id);
}
