package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.Departament;
import com.conovax.sexbody.domain.repositories.DepartamentRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.DepartamentJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.DepartamentMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.DepartamentJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DepartamentRepositoryAdapter implements DepartamentRepository {

	private final DepartamentJpaRepository jpaRepository;
	private final DepartamentMapper mapper;

	public DepartamentRepositoryAdapter(DepartamentJpaRepository jpaRepository, DepartamentMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Departament save(Departament departament) {
		DepartamentJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(departament));
		return mapper.toDomain(saved);
	}

	@Override
	public List<Departament> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Departament> findAllByStatusTrue(Pageable pageable) {
		return jpaRepository.findAllByStatusTrue(pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Departament> findAllByStatusTrueAndNameContainingIgnoreCase(String name, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndNameContainingIgnoreCase(name, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Departament> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Departament> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public List<Departament> findAllByContryIdAndStatusTrue(Long contryId) {
		return jpaRepository.findAllByContryIdAndStatusTrue(contryId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Departament> findAllByContryIdAndStatusTrue(Long contryId, Pageable pageable) {
		return jpaRepository.findAllByContryIdAndStatusTrue(contryId, pageable)
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
}
