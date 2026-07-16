package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PeopleRepository {
	Page<People> findAllByStatusTrue(Pageable pageable);

	Optional<People> findByIdAndStatusTrue(Long id);

	Optional<People> findByIdAndStatusFalse(Long id);

	boolean existsByNumDocumentAndStatusTrue(String numDocument);

	boolean existsByEmailAndStatusTrue(String email);

	boolean existsByNumDocumentAndStatusTrueAndIdNot(String numDocument, Long id);

	boolean existsByEmailAndStatusTrueAndIdNot(String email, Long id);

	People save(People people);

	boolean existsById(Long id);

	void deleteById(Long id);
}
