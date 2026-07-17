package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.SaasFeatureJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaasFeatureJpaRepository extends JpaRepository<SaasFeatureJpaEntity, Long> {
	List<SaasFeatureJpaEntity> findAllByStatusTrue();

	Optional<SaasFeatureJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<SaasFeatureJpaEntity> findByIdAndStatusFalse(Long id);

	boolean existsByCodeIgnoreCase(String code);

	boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id);
}