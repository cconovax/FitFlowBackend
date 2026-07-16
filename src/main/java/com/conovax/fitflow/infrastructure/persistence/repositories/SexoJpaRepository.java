package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.SexoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SexoJpaRepository extends JpaRepository<SexoJpaEntity, Long> {
	List<SexoJpaEntity> findAllByStatusTrue();

	Optional<SexoJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<SexoJpaEntity> findByIdAndStatusFalse(Long id);
}
