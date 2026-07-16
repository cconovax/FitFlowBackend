package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.Municipality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MunicipalityRepository {
	Municipality save(Municipality municipality);

	List<Municipality> findAllByStatusTrue();

	Page<Municipality> findAllByStatusTrue(Pageable pageable);

	Page<Municipality> findAllByStatusTrueAndNameContainingIgnoreCase(String name, Pageable pageable);

	Optional<Municipality> findByIdAndStatusTrue(Long id);

	Optional<Municipality> findByIdAndStatusFalse(Long id);

	List<Municipality> findAllByDepartamentIdAndStatusTrue(Long departamentId);

	boolean existsById(Long id);

	void deleteById(Long id);
}
