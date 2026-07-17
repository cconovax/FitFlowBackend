package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.PeopleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface PeopleJpaRepository extends JpaRepository<PeopleJpaEntity, Long> {
	Page<PeopleJpaEntity> findAllByStatusTrue(Pageable pageable);

	Optional<PeopleJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<PeopleJpaEntity> findByIdAndStatusFalse(Long id);

	boolean existsByNumDocumentAndStatusTrue(String numDocument);

	boolean existsByEmailAndStatusTrue(String email);

	boolean existsByNumDocumentAndStatusTrueAndIdNot(String numDocument, Long id);

	boolean existsByEmailAndStatusTrueAndIdNot(String email, Long id);
}
