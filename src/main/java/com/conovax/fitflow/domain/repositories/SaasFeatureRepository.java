package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.SaasFeature;

import java.util.List;
import java.util.Optional;

public interface SaasFeatureRepository {
	SaasFeature save(SaasFeature feature);

	List<SaasFeature> findAllByStatusTrue();

	List<SaasFeature> findAll();

	List<SaasFeature> findAllById(Iterable<Long> ids);

	Optional<SaasFeature> findByIdAndStatusTrue(Long id);

	Optional<SaasFeature> findByIdAndStatusFalse(Long id);

	Optional<SaasFeature> findById(Long id);

	boolean existsByCodeIgnoreCase(String code);

	boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id);

	boolean existsById(Long id);

	void deleteById(Long id);
}