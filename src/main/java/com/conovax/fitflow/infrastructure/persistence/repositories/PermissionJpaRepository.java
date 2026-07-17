package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.PermissionJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionJpaEntity, Long> {

	Optional<PermissionJpaEntity> findBySlug(String slug);

	boolean existsBySlug(String slug);

	boolean existsBySlugAndIdNot(String slug, Long id);

	List<PermissionJpaEntity> findAllByStatusTrue();

	Page<PermissionJpaEntity> findAllByBasicTrue(Pageable pageable);

	Page<PermissionJpaEntity> findBySlugContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

	Page<PermissionJpaEntity> findByBasicTrueAndSlugContainingIgnoreCaseOrBasicTrueAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

	Page<PermissionJpaEntity> findAllByStatusTrue(Pageable pageable);

	Page<PermissionJpaEntity> findAllByStatusTrueAndSlugContainingIgnoreCase(String slug, Pageable pageable);

	Page<PermissionJpaEntity> findAllByStatusTrueAndDescriptionContainingIgnoreCase(String description, Pageable pageable);

	Page<PermissionJpaEntity> findAllByStatusTrueAndSlugContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

	Page<PermissionJpaEntity> findAllByStatusTrueAndBasicTrue(Pageable pageable);

	Page<PermissionJpaEntity> findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCase(String slug, Pageable pageable);

	Page<PermissionJpaEntity> findAllByStatusTrueAndBasicTrueAndDescriptionContainingIgnoreCase(String description, Pageable pageable);

	Page<PermissionJpaEntity> findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

	Page<PermissionJpaEntity> findAllByStatusTrueAndSlugContainingIgnoreCaseOrStatusTrueAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

	Page<PermissionJpaEntity> findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCaseOrStatusTrueAndBasicTrueAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

	Optional<PermissionJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<PermissionJpaEntity> findByIdAndStatusFalse(Long id);
}
