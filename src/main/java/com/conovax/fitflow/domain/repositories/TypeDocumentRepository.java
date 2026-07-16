package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.TypeDocument;

import java.util.List;
import java.util.Optional;

public interface TypeDocumentRepository {
	List<TypeDocument> findAllByStatusTrue();

	Optional<TypeDocument> findByIdAndStatusTrue(Long id);

	Optional<TypeDocument> findByIdAndStatusFalse(Long id);

	TypeDocument save(TypeDocument typeDocument);

	boolean existsById(Long id);

	void deleteById(Long id);
}
