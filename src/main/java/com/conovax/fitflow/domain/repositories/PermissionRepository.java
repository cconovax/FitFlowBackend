package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository {
    Permission save(Permission permission);

    Optional<Permission> findById(Long id);

    Optional<Permission> findBySlug(String slug);

    boolean existsBySlug(String slug);

    boolean existsBySlugAndIdNot(String slug, Long id);

    List<Permission> findAllByStatusTrue();

    Page<Permission> findAllByStatusTrue(Pageable pageable);

    Page<Permission> findAllByStatusTrueAndSlugContainingIgnoreCase(String slug, Pageable pageable);

    Page<Permission> findAllByStatusTrueAndDescriptionContainingIgnoreCase(String description, Pageable pageable);

    Page<Permission> findAllByStatusTrueAndSlugContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

    Page<Permission> findAllByStatusTrueAndBasicTrue(Pageable pageable);

    Page<Permission> findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCase(String slug, Pageable pageable);

    Page<Permission> findAllByStatusTrueAndBasicTrueAndDescriptionContainingIgnoreCase(String description, Pageable pageable);

    Page<Permission> findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

    Page<Permission> findAllByStatusTrueAndSlugContainingIgnoreCaseOrStatusTrueAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

    Page<Permission> findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCaseOrStatusTrueAndBasicTrueAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

    Optional<Permission> findByIdAndStatusTrue(Long id);

    Optional<Permission> findByIdAndStatusFalse(Long id);

    List<Permission> findAll();

    Page<Permission> findAll(Pageable pageable);

    Page<Permission> findAllByBasicTrue(Pageable pageable);

    Page<Permission> findBySlugContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

    Page<Permission> findByBasicTrueAndSlugContainingIgnoreCaseOrBasicTrueAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable);

    boolean existsById(Long id);

    void deleteById(Long id);

    long count();
}
