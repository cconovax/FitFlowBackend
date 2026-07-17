package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.Role;
import com.conovax.fitflow.domain.repositories.RoleRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.RoleJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.RoleMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.RoleJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryAdapter implements RoleRepository {

	private final RoleJpaRepository jpaRepository;
	private final RoleMapper mapper;

	public RoleRepositoryAdapter(RoleJpaRepository jpaRepository, RoleMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Role save(Role role) {
		RoleJpaEntity jpaEntity = mapper.toJpaEntity(role);
		RoleJpaEntity saved = jpaRepository.save(jpaEntity);
		return mapper.toDomain(saved);
	}

	@Override
	public Optional<Role> findById(Long id) {
		return jpaRepository.findById(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Role> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Role> findByName(String name) {
		return jpaRepository.findByNameAndStatusTrue(name)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Role> findByNameAndStatusTrue(String name) {
		return jpaRepository.findByNameAndStatusTrue(name)
				.map(mapper::toDomain);
	}

	@Override
	public List<Role> findAllByGymId(Long gymId) {
		return jpaRepository.findAllByGymIdAndStatusTrue(gymId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Role> findAllByGymIdAndStatusTrue(Long gymId) {
		return jpaRepository.findAllByGymIdAndStatusTrue(gymId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Role> findAllByGymIdAndStatusTrue(Long gymId, Pageable pageable) {
		return jpaRepository.findAllByGymIdAndStatusTrue(gymId, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public List<Role> findAllByGymIdOrUniversal(Long gymId) {
		return jpaRepository.findAllByGymIdOrGymIdIsNullAndStatusTrue(gymId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Role> findAllByGymIdOrGymIdIsNullAndStatusTrue(Long gymId) {
		return jpaRepository.findAllByGymIdOrGymIdIsNullAndStatusTrue(gymId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Role> findAllByGymIdOrGymIdIsNullAndStatusTrue(Long gymId, Pageable pageable) {
		return jpaRepository.findAllByGymIdOrGymIdIsNullAndStatusTrue(gymId, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public List<Role> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Role> findAllByStatusTrue(Pageable pageable) {
		return jpaRepository.findAllByStatusTrue(pageable)
				.map(mapper::toDomain);
	}

	@Override
	public List<Role> findAllById(Iterable<Long> ids) {
		return jpaRepository.findAllById(ids).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Role> findAll() {
		return jpaRepository.findAll().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Role> findAll(Pageable pageable) {
		return jpaRepository.findAll(pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Role> findAllByGymId(Long gymId, Pageable pageable) {
		return jpaRepository.findAllByGymIdAndStatusTrue(gymId, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public long count() {
		return jpaRepository.count();
	}

	@Override
	public boolean existsByName(String name) {
		return jpaRepository.existsByName(name);
	}

	@Override
	public boolean existsById(Long id) {
		return jpaRepository.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}
}
