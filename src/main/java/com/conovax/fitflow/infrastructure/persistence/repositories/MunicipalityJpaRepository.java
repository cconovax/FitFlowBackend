package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.MunicipalityJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MunicipalityJpaRepository extends JpaRepository<MunicipalityJpaEntity, Long> {
	List<MunicipalityJpaEntity> findAllByStatusTrue();

	Page<MunicipalityJpaEntity> findAllByStatusTrue(Pageable pageable);

	Page<MunicipalityJpaEntity> findAllByStatusTrueAndNameContainingIgnoreCase(String name, Pageable pageable);

	Optional<MunicipalityJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<MunicipalityJpaEntity> findByIdAndStatusFalse(Long id);

	List<MunicipalityJpaEntity> findAllByDepartamentIdAndStatusTrue(Long departamentId);
}
