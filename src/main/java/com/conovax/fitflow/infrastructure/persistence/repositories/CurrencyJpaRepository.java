package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.CurrencyJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyJpaRepository extends JpaRepository<CurrencyJpaEntity, Long> {

	@Query(
			"""
			SELECT c
			FROM CurrencyJpaEntity c
			WHERE c.status = true
				AND (
					:search IS NULL OR :search = ''
					OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))
					OR LOWER(c.code) LIKE LOWER(CONCAT('%', :search, '%'))
				)
			"""
	)
	Page<CurrencyJpaEntity> findAllByStatusTrueAndSearchFilter(
			@Param("search") String search,
			Pageable pageable
	);

	@Query(
			"""
			SELECT c
			FROM CurrencyJpaEntity c
			WHERE (
					:search IS NULL OR :search = ''
					OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))
					OR LOWER(c.code) LIKE LOWER(CONCAT('%', :search, '%'))
				)
			"""
	)
	Page<CurrencyJpaEntity> findAllWithSearchFilter(
			@Param("search") String search,
			Pageable pageable
	);

	Optional<CurrencyJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<CurrencyJpaEntity> findByIdAndStatusFalse(Long id);
}