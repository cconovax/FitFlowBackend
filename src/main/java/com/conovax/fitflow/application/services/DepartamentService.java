package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.DepartamentRequest;
import com.conovax.sexbody.application.dto.response.DepartamentResponse;
import com.conovax.sexbody.application.pagination.PageResponse;
import com.conovax.sexbody.application.pagination.PaginationUtils;
import com.conovax.sexbody.domain.entities.Departament;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.CountryRepository;
import com.conovax.sexbody.domain.repositories.DepartamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentService {

	private final DepartamentRepository departamentRepository;
	private final CountryRepository countryRepository;

	@Transactional(readOnly = true)
	public List<DepartamentResponse> getAll() {
		return departamentRepository.findAllByStatusTrue().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public PageResponse<DepartamentResponse> getAll(Integer page, Integer size, String name) {
		Pageable pageable = PaginationUtils.pageable(page, size);

		Page<Departament> departaments = (name == null || name.isBlank())
				? departamentRepository.findAllByStatusTrue(pageable)
				: departamentRepository.findAllByStatusTrueAndNameContainingIgnoreCase(name, pageable);

		return PaginationUtils.map(departaments, this::toResponse);
	}

	@Transactional(readOnly = true)
	public DepartamentResponse getById(Long id) {
		Departament entity = departamentRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));
		return toResponse(entity);
	}

	@Transactional(readOnly = true)
	public List<DepartamentResponse> getAllByCountry(Long countryId) {
		countryRepository.findByIdAndStatusTrue(countryId)
				.orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + countryId));

		return departamentRepository.findAllByContryIdAndStatusTrue(countryId)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public PageResponse<DepartamentResponse> getAllByCountry(Long countryId, Integer page, Integer size) {
		countryRepository.findByIdAndStatusTrue(countryId)
				.orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + countryId));

		Pageable pageable = PaginationUtils.pageable(page, size);
		Page<Departament> departaments = departamentRepository.findAllByContryIdAndStatusTrue(countryId, pageable);
		return PaginationUtils.map(departaments, this::toResponse);
	}

	@Transactional
	public DepartamentResponse create(DepartamentRequest request) {
		countryRepository.findByIdAndStatusTrue(request.countryId())
				.orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + request.countryId()));

		Departament entity = Departament.builder()
				.contryId(request.countryId())
				.name(request.name())
				.status(true)
				.build();

		return toResponse(departamentRepository.save(entity));
	}

	@Transactional
	public DepartamentResponse update(Long id, DepartamentRequest request) {
		Departament entity = departamentRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));

		countryRepository.findByIdAndStatusTrue(request.countryId())
				.orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + request.countryId()));

		Departament updated = entity.toBuilder()
				.contryId(request.countryId())
				.name(request.name())
				.build();
		return toResponse(departamentRepository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long id) {
		Departament entity = departamentRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));
		departamentRepository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		Departament entity = departamentRepository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));
		departamentRepository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!departamentRepository.existsById(id)) {
			throw new ResourceNotFoundException("Departamento no encontrado con ID: " + id);
		}
		departamentRepository.deleteById(id);
	}

	private DepartamentResponse toResponse(Departament entity) {
		return new DepartamentResponse(entity.getId(), entity.getContryId(), entity.getName(), entity.getStatus());
	}
}
