package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.Departament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DepartamentRepository {
	Departament save(Departament departament);

	List<Departament> findAllByStatusTrue();

	Page<Departament> findAllByStatusTrue(Pageable pageable);

	Page<Departament> findAllByStatusTrueAndNameContainingIgnoreCase(String name, Pageable pageable);

	Optional<Departament> findByIdAndStatusTrue(Long id);

	Optional<Departament> findByIdAndStatusFalse(Long id);

	List<Departament> findAllByContryIdAndStatusTrue(Long contryId);

	Page<Departament> findAllByContryIdAndStatusTrue(Long contryId, Pageable pageable);

	boolean existsById(Long id);

	void deleteById(Long id);
}
