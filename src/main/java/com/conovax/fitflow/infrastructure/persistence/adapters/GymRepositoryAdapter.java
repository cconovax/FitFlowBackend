package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.Gym;
import com.conovax.sexbody.domain.repositories.GymRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.GymJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.GymMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.GymJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GymRepositoryAdapter implements GymRepository {

	private final GymJpaRepository jpaRepository;
	private final GymMapper mapper;

	public GymRepositoryAdapter(GymJpaRepository jpaRepository, GymMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Gym save(Gym gym) {
		GymJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(gym));
		return mapper.toDomain(saved);
	}

	@Override
	public List<Gym> findAll() {
		return jpaRepository.findAll().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Gym> findAll(Pageable pageable) {
		return jpaRepository.findAll(pageable)
				.map(mapper::toDomain);
	}

	@Override
	public List<Gym> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Gym> findById(Long id) {
		return jpaRepository.findById(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Gym> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Gym> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public List<Gym> findByNameContainingIgnoreCaseAndStatusTrue(String name) {
		return jpaRepository.findByNameContainingIgnoreCaseAndStatusTrue(name).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Gym> findByNameContainingIgnoreCase(String name) {
		return jpaRepository.findByNameContainingIgnoreCase(name).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Gym> findByNameContainingIgnoreCase(String name, Pageable pageable) {
		return jpaRepository.findByNameContainingIgnoreCase(name, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Gym> findByNameContainingIgnoreCaseOrNitContainingIgnoreCase(String name, String nit, Pageable pageable) {
		return jpaRepository.findByNameContainingIgnoreCaseOrNitContainingIgnoreCase(name, nit, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public List<Gym> findByParentGymId(Long parentGymId) {
		return jpaRepository.findByParentGym_Id(parentGymId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
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
