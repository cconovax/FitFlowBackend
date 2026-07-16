package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.PeopleRequest;
import com.conovax.sexbody.application.dto.response.PeopleResponse;
import com.conovax.sexbody.application.pagination.PageResponse;
import com.conovax.sexbody.application.pagination.PaginationUtils;
import com.conovax.sexbody.domain.entities.People;
import com.conovax.sexbody.domain.exceptions.DuplicateResourceException;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.MunicipalityRepository;
import com.conovax.sexbody.domain.repositories.PeopleRepository;
import com.conovax.sexbody.domain.repositories.SexoRepository;
import com.conovax.sexbody.domain.repositories.TypeDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PeopleService {

	private static final String DEFAULT_PROFILE_PHOTO_URL = "/uploads/logo/profile-default.svg";

	private final PeopleRepository peopleRepository;
	private final MunicipalityRepository municipalityRepository;
	private final SexoRepository sexoRepository;
	private final TypeDocumentRepository typeDocumentRepository;

	@Transactional(readOnly = true)
	public PageResponse<PeopleResponse> getAll(Integer page, Integer size) {
		Pageable pageable = PaginationUtils.pageable(page, size);
		Page<People> peoples = peopleRepository.findAllByStatusTrue(pageable);
		return PaginationUtils.map(peoples, this::toResponse);
	}

	@Transactional(readOnly = true)
	public PeopleResponse getById(Long peopleId) {
		People entity = peopleRepository.findByIdAndStatusTrue(peopleId)
				.orElseThrow(() -> new ResourceNotFoundException("People no encontrado con ID: " + peopleId));
		return toResponse(entity);
	}

	@Transactional(readOnly = true)
	public boolean isNumDocumentRegistered(String numDocument) {
		if (numDocument == null || numDocument.isBlank()) {
			throw new IllegalArgumentException("El numDocument es requerido");
		}
		return peopleRepository.existsByNumDocumentAndStatusTrue(numDocument);
	}

	@Transactional
	public PeopleResponse create(PeopleRequest request) {
		validateForeignKeys(request.municipalitieId(), request.sexoId(), request.typeDocumentId());
		validateDuplicatesOnCreate(request.numDocument(), request.email());

		String photo = (request.photo() == null || request.photo().isBlank())
				? DEFAULT_PROFILE_PHOTO_URL
				: request.photo();

		People entity = People.builder()
				.names(request.names())
				.surnames(request.surnames())
				.phone(request.phone())
				.email(request.email())
				.photo(photo)
				.municipalitieId(request.municipalitieId())
				.sexoId(request.sexoId())
				.typeDocumentId(request.typeDocumentId())
				.numDocument(request.numDocument())
				.status(true)
				.build();

		return toResponse(peopleRepository.save(entity));
	}

	@Transactional
	public PeopleResponse update(Long peopleId, PeopleRequest request) {
		People entity = peopleRepository.findByIdAndStatusTrue(peopleId)
				.orElseThrow(() -> new ResourceNotFoundException("People no encontrado con ID: " + peopleId));

		validateForeignKeys(request.municipalitieId(), request.sexoId(), request.typeDocumentId());
		validateDuplicatesOnUpdate(peopleId, request.numDocument(), request.email());

		String photo = entity.getPhoto();
		if (request.photo() != null && !request.photo().isBlank()) {
			photo = request.photo();
		} else if (photo == null || photo.isBlank()) {
			photo = DEFAULT_PROFILE_PHOTO_URL;
		}

		People updated = entity.toBuilder()
				.names(request.names())
				.surnames(request.surnames())
				.phone(request.phone())
				.email(request.email())
				.photo(photo)
				.municipalitieId(request.municipalitieId())
				.sexoId(request.sexoId())
				.typeDocumentId(request.typeDocumentId())
				.numDocument(request.numDocument())
				.build();

		return toResponse(peopleRepository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long peopleId) {
		People entity = peopleRepository.findByIdAndStatusTrue(peopleId)
				.orElseThrow(() -> new ResourceNotFoundException("People no encontrado con ID: " + peopleId));
		peopleRepository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long peopleId) {
		People entity = peopleRepository.findByIdAndStatusFalse(peopleId)
				.orElseThrow(() -> new ResourceNotFoundException("People no encontrado con ID: " + peopleId));
		peopleRepository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long peopleId) {
		if (!peopleRepository.existsById(peopleId)) {
			throw new ResourceNotFoundException("People no encontrado con ID: " + peopleId);
		}
		peopleRepository.deleteById(peopleId);
	}

	private void validateForeignKeys(Long municipalitieId, Long sexoId, Long typeDocumentId) {
		municipalityRepository.findByIdAndStatusTrue(municipalitieId)
				.orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + municipalitieId));
		sexoRepository.findByIdAndStatusTrue(sexoId)
				.orElseThrow(() -> new ResourceNotFoundException("Sexo no encontrado con ID: " + sexoId));
		typeDocumentRepository.findByIdAndStatusTrue(typeDocumentId)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado con ID: " + typeDocumentId));
	}

	private void validateDuplicatesOnCreate(String numDocument, String email) {
		if (peopleRepository.existsByNumDocumentAndStatusTrue(numDocument)) {
			throw new DuplicateResourceException("El número de documento ya está registrado");
		}
		if (email != null && !email.isBlank() && peopleRepository.existsByEmailAndStatusTrue(email)) {
			throw new DuplicateResourceException("El email ya está registrado");
		}
	}

	private void validateDuplicatesOnUpdate(Long id, String numDocument, String email) {
		if (peopleRepository.existsByNumDocumentAndStatusTrueAndIdNot(numDocument, id)) {
			throw new DuplicateResourceException("El número de documento ya está registrado");
		}
		if (email != null && !email.isBlank() && peopleRepository.existsByEmailAndStatusTrueAndIdNot(email, id)) {
			throw new DuplicateResourceException("El email ya está registrado");
		}
	}

	private PeopleResponse toResponse(People p) {
		return new PeopleResponse(
				p.getId(),
				p.getNames(),
				p.getSurnames(),
				p.getPhone(),
				p.getEmail(),
				p.getPhoto(),
				p.getMunicipalitieId(),
				p.getSexoId(),
				p.getTypeDocumentId(),
				p.getNumDocument(),
				p.getStatus(),
				null,
				null
		);
	}
}
