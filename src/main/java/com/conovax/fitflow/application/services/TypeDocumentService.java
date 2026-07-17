package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.TypeDocumentRequest;
import com.conovax.fitflow.application.dto.response.TypeDocumentResponse;
import com.conovax.fitflow.domain.entities.TypeDocument;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.TypeDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeDocumentService {

	private final TypeDocumentRepository typeDocumentRepository;

	@Transactional(readOnly = true)
	public List<TypeDocumentResponse> getAll() {
		return typeDocumentRepository.findAllByStatusTrue().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public TypeDocumentResponse getById(Long id) {
		TypeDocument entity = typeDocumentRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado con ID: " + id));
		return toResponse(entity);
	}

	@Transactional
	public TypeDocumentResponse create(TypeDocumentRequest request) {
		TypeDocument entity = TypeDocument.builder()
				.name(request.name())
				.code(request.code())
				.status(true)
				.build();
		return toResponse(typeDocumentRepository.save(entity));
	}

	@Transactional
	public TypeDocumentResponse update(Long id, TypeDocumentRequest request) {
		TypeDocument entity = typeDocumentRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado con ID: " + id));
		TypeDocument updated = entity.toBuilder()
				.name(request.name())
				.code(request.code())
				.build();
		return toResponse(typeDocumentRepository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long id) {
		TypeDocument entity = typeDocumentRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado con ID: " + id));
		typeDocumentRepository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		TypeDocument entity = typeDocumentRepository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado con ID: " + id));
		typeDocumentRepository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!typeDocumentRepository.existsById(id)) {
			throw new ResourceNotFoundException("Tipo de documento no encontrado con ID: " + id);
		}
		typeDocumentRepository.deleteById(id);
	}

	private TypeDocumentResponse toResponse(TypeDocument entity) {
		return new TypeDocumentResponse(entity.getId(), entity.getName(), entity.getCode(), entity.getStatus());
	}
}
