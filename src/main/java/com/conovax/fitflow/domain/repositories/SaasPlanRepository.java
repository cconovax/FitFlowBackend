package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.SaasPlan;

import java.util.List;
import java.util.Optional;

public interface SaasPlanRepository {
	SaasPlan save(SaasPlan plan);

	List<SaasPlan> findAllByStatusTrue();

	List<SaasPlan> findAll();

	Optional<SaasPlan> findByIdAndStatusTrue(Long id);

	Optional<SaasPlan> findByIdAndStatusFalse(Long id);

	Optional<SaasPlan> findById(Long id);

	boolean existsByCodeIgnoreCase(String code);

	boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id);

	boolean existsById(Long id);

	void deleteById(Long id);
}