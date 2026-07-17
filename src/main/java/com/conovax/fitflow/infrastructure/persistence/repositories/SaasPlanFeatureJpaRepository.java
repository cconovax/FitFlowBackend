package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.SaasPlanFeatureJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaasPlanFeatureJpaRepository extends JpaRepository<SaasPlanFeatureJpaEntity, Long> {
	List<SaasPlanFeatureJpaEntity> findAllBySaasPlan_Id(Long saasPlanId);

	void deleteBySaasPlan_Id(Long saasPlanId);
}