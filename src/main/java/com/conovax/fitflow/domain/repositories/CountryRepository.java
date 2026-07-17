package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {
	Country save(Country country);

	List<Country> findAllByStatusTrue();

	Page<Country> findAllByStatusTrue(Pageable pageable);

	Page<Country> findAllByStatusTrueAndNameContainingIgnoreCase(String name, Pageable pageable);

	Optional<Country> findByIdAndStatusTrue(Long id);

	Optional<Country> findByIdAndStatusFalse(Long id);

	List<Country> findByNameContainingIgnoreCaseAndStatusTrue(String name);

	boolean existsById(Long id);

	void deleteById(Long id);
}
