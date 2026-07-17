package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.Country;
import com.conovax.fitflow.domain.repositories.CountryRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.CountryJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.CountryMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.CountryJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CountryRepositoryAdapter implements CountryRepository {

	private final CountryJpaRepository jpaRepository;
	private final CountryMapper mapper;

	public CountryRepositoryAdapter(CountryJpaRepository jpaRepository, CountryMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Country save(Country country) {
		CountryJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(country));
		return mapper.toDomain(saved);
	}

	@Override
	public List<Country> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Country> findAllByStatusTrue(Pageable pageable) {
		return jpaRepository.findAllByStatusTrue(pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Country> findAllByStatusTrueAndNameContainingIgnoreCase(String name, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndNameContainingIgnoreCase(name, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Country> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Country> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public List<Country> findByNameContainingIgnoreCaseAndStatusTrue(String name) {
		return jpaRepository.findByNameContainingIgnoreCaseAndStatusTrue(name).stream()
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
