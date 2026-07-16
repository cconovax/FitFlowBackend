package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.SaasPlanJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaasPlanJpaRepository extends JpaRepository<SaasPlanJpaEntity, Long> {
	List<SaasPlanJpaEntity> findAllByStatusTrue();

	Optional<SaasPlanJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<SaasPlanJpaEntity> findByIdAndStatusFalse(Long id);

	boolean existsByCodeIgnoreCase(String code);

	boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id);
}