package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.DepartamentJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartamentJpaRepository extends JpaRepository<DepartamentJpaEntity, Long> {
	List<DepartamentJpaEntity> findAllByStatusTrue();

	Page<DepartamentJpaEntity> findAllByStatusTrue(Pageable pageable);

	Page<DepartamentJpaEntity> findAllByStatusTrueAndNameContainingIgnoreCase(String name, Pageable pageable);

	Optional<DepartamentJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<DepartamentJpaEntity> findByIdAndStatusFalse(Long id);

	List<DepartamentJpaEntity> findAllByContryIdAndStatusTrue(Long contryId);

	Page<DepartamentJpaEntity> findAllByContryIdAndStatusTrue(Long contryId, Pageable pageable);
}
