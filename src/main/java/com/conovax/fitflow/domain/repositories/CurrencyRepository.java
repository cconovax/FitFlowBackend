package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CurrencyRepository {
	Page<Currency> findAllByStatusTrueAndSearchFilter(String search, Pageable pageable);

	Page<Currency> findAllWithSearchFilter(String search, Pageable pageable);

	Optional<Currency> findByIdAndStatusTrue(Long id);

	Optional<Currency> findByIdAndStatusFalse(Long id);

	Currency save(Currency currency);

	boolean existsById(Long id);

	void deleteById(Long id);
}
