package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.Sexo;
import com.conovax.fitflow.domain.repositories.SexoRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.SexoJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.SexoMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.SexoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SexoRepositoryAdapter implements SexoRepository {

	private final SexoJpaRepository jpaRepository;
	private final SexoMapper mapper;

	public SexoRepositoryAdapter(SexoJpaRepository jpaRepository, SexoMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public List<Sexo> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Sexo> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Sexo> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public Sexo save(Sexo sexo) {
		SexoJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(sexo));
		return mapper.toDomain(saved);
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
