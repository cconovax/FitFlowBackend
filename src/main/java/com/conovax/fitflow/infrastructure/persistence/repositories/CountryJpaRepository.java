package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.CountryJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryJpaRepository extends JpaRepository<CountryJpaEntity, Long> {
	List<CountryJpaEntity> findAllByStatusTrue();

	Page<CountryJpaEntity> findAllByStatusTrue(Pageable pageable);

	Page<CountryJpaEntity> findAllByStatusTrueAndNameContainingIgnoreCase(String name, Pageable pageable);

	Optional<CountryJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<CountryJpaEntity> findByIdAndStatusFalse(Long id);

	List<CountryJpaEntity> findByNameContainingIgnoreCaseAndStatusTrue(String name);
}
