package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.Municipality;
import com.conovax.sexbody.domain.repositories.MunicipalityRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.MunicipalityJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.MunicipalityMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.MunicipalityJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MunicipalityRepositoryAdapter implements MunicipalityRepository {

	private final MunicipalityJpaRepository jpaRepository;
	private final MunicipalityMapper mapper;

	public MunicipalityRepositoryAdapter(MunicipalityJpaRepository jpaRepository, MunicipalityMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Municipality save(Municipality municipality) {
		MunicipalityJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(municipality));
		return mapper.toDomain(saved);
	}

	@Override
	public List<Municipality> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Municipality> findAllByStatusTrue(Pageable pageable) {
		return jpaRepository.findAllByStatusTrue(pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Municipality> findAllByStatusTrueAndNameContainingIgnoreCase(String name, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndNameContainingIgnoreCase(name, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Municipality> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Municipality> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public List<Municipality> findAllByDepartamentIdAndStatusTrue(Long departamentId) {
		return jpaRepository.findAllByDepartamentIdAndStatusTrue(departamentId).stream()
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
