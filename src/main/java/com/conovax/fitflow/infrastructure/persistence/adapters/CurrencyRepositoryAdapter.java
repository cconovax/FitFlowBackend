package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.Currency;
import com.conovax.sexbody.domain.repositories.CurrencyRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.CurrencyJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.CurrencyMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.CurrencyJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CurrencyRepositoryAdapter implements CurrencyRepository {

	private final CurrencyJpaRepository jpaRepository;
	private final CurrencyMapper mapper;

	public CurrencyRepositoryAdapter(CurrencyJpaRepository jpaRepository, CurrencyMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Page<Currency> findAllByStatusTrueAndSearchFilter(String search, Pageable pageable) {
		return jpaRepository.findAllByStatusTrueAndSearchFilter(search, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Currency> findAllWithSearchFilter(String search, Pageable pageable) {
		return jpaRepository.findAllWithSearchFilter(search, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Currency> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Currency> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public Currency save(Currency currency) {
		CurrencyJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(currency));
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
