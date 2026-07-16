package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.Permission;
import com.conovax.sexbody.domain.repositories.PermissionRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.PermissionJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.PermissionMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.PermissionJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PermissionRepositoryAdapter implements PermissionRepository {

	private final PermissionJpaRepository jpaRepository;
	private final PermissionMapper mapper;

	public PermissionRepositoryAdapter(PermissionJpaRepository jpaRepository, PermissionMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Permission save(Permission permission) {
		PermissionJpaEntity jpaEntity = mapper.toJpaEntity(permission);
		PermissionJpaEntity saved = jpaRepository.save(jpaEntity);
		return mapper.toDomain(saved);
	}

	@Override
	public Optional<Permission> findById(Long id) {
		return jpaRepository.findById(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Permission> findBySlug(String slug) {
		return jpaRepository.findBySlug(slug)
				.map(mapper::toDomain);
	}

	@Override
	public boolean existsBySlug(String slug) {
		return jpaRepository.existsBySlug(slug);
	}

	@Override
	public boolean existsBySlugAndIdNot(String slug, Long id) {
		return jpaRepository.existsBySlugAndIdNot(slug, id);
	}

	@Override
	public List<Permission> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Permission> findAllByStatusTrue(Pageable pageable) {
		return jpaRepository.findAllByStatusTrue(pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findAllByStatusTrueAndSlugContainingIgnoreCase(String slug, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndSlugContainingIgnoreCase(slug, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findAllByStatusTrueAndDescriptionContainingIgnoreCase(String description, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndDescriptionContainingIgnoreCase(description, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findAllByStatusTrueAndSlugContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndSlugContainingIgnoreCaseAndDescriptionContainingIgnoreCase(slug, description, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findAllByStatusTrueAndBasicTrue(Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndBasicTrue(pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCase(String slug, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCase(slug, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findAllByStatusTrueAndBasicTrueAndDescriptionContainingIgnoreCase(String description, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndBasicTrueAndDescriptionContainingIgnoreCase(description, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCaseAndDescriptionContainingIgnoreCase(slug, description, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findAllByStatusTrueAndSlugContainingIgnoreCaseOrStatusTrueAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndSlugContainingIgnoreCaseOrStatusTrueAndDescriptionContainingIgnoreCase(slug, description, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCaseOrStatusTrueAndBasicTrueAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndBasicTrueAndSlugContainingIgnoreCaseOrStatusTrueAndBasicTrueAndDescriptionContainingIgnoreCase(slug, description, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Permission> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Permission> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public List<Permission> findAll() {
		return jpaRepository.findAll().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Permission> findAll(Pageable pageable) {
		return jpaRepository.findAll(pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findAllByBasicTrue(Pageable pageable) {
		return jpaRepository.findAllByBasicTrue(pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findBySlugContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable) {
		return jpaRepository.findBySlugContainingIgnoreCaseOrDescriptionContainingIgnoreCase(slug, description, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Permission> findByBasicTrueAndSlugContainingIgnoreCaseOrBasicTrueAndDescriptionContainingIgnoreCase(String slug, String description, Pageable pageable) {
		return jpaRepository.findByBasicTrueAndSlugContainingIgnoreCaseOrBasicTrueAndDescriptionContainingIgnoreCase(slug, description, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public boolean existsById(Long id) {
		return jpaRepository.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public long count() {
		return jpaRepository.count();
	}
}
