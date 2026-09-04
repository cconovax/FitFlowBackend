package com.conovax.fitflow.subscription.domain.repositories;

import com.conovax.fitflow.subscription.domain.entities.SaasPlanFeature;

import java.util.List;

public interface SaasPlanFeatureRepository {
	SaasPlanFeature save(SaasPlanFeature entity);

	List<SaasPlanFeature> saveAll(List<SaasPlanFeature> entities);

	List<SaasPlanFeature> findAllBySaasPlanId(Long saasPlanId);

	void deleteBySaasPlanId(Long saasPlanId);
}