package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.TypeDocumentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeDocumentJpaRepository extends JpaRepository<TypeDocumentJpaEntity, Long> {
	List<TypeDocumentJpaEntity> findAllByStatusTrue();

	Optional<TypeDocumentJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<TypeDocumentJpaEntity> findByIdAndStatusFalse(Long id);
}
