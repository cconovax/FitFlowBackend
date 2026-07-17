package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.MunicipalityRequest;
import com.conovax.fitflow.application.dto.response.MunicipalityResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.pagination.PaginationUtils;
import com.conovax.fitflow.domain.entities.Municipality;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.DepartamentRepository;
import com.conovax.fitflow.domain.repositories.MunicipalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MunicipalityService {

	private final MunicipalityRepository municipalityRepository;
	private final DepartamentRepository departamentRepository;

	@Transactional(readOnly = true)
	public List<MunicipalityResponse> getAll() {
		return municipalityRepository.findAllByStatusTrue().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public PageResponse<MunicipalityResponse> getAll(Integer page, Integer size) {
		return getAll(page, size, null);
	}

	@Transactional(readOnly = true)
	public PageResponse<MunicipalityResponse> getAll(Integer page, Integer size, String name) {
		Pageable pageable = PaginationUtils.pageable(page, size);

		Page<Municipality> municipalities;
		if (name == null || name.isBlank()) {
			municipalities = municipalityRepository.findAllByStatusTrue(pageable);
		} else {
			municipalities = municipalityRepository.findAllByStatusTrueAndNameContainingIgnoreCase(name.trim(), pageable);
		}

		return PaginationUtils.map(municipalities, this::toResponse);
	}

	@Transactional(readOnly = true)
	public MunicipalityResponse getById(Long id) {
		Municipality entity = municipalityRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + id));
		return toResponse(entity);
	}

	@Transactional(readOnly = true)
	public List<MunicipalityResponse> getAllByDepartament(Long departamentId) {
		departamentRepository.findByIdAndStatusTrue(departamentId)
				.orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + departamentId));

		return municipalityRepository.findAllByDepartamentIdAndStatusTrue(departamentId)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional
	public MunicipalityResponse create(MunicipalityRequest request) {
		departamentRepository.findByIdAndStatusTrue(request.departamentId())
				.orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + request.departamentId()));

		Municipality entity = Municipality.builder()
				.departamentId(request.departamentId())
				.name(request.name())
				.status(true)
				.build();

		return toResponse(municipalityRepository.save(entity));
	}

	@Transactional
	public MunicipalityResponse update(Long id, MunicipalityRequest request) {
		Municipality entity = municipalityRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + id));

		departamentRepository.findByIdAndStatusTrue(request.departamentId())
				.orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + request.departamentId()));

		Municipality updated = entity.toBuilder()
				.departamentId(request.departamentId())
				.name(request.name())
				.build();
		return toResponse(municipalityRepository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long id) {
		Municipality entity = municipalityRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + id));
		municipalityRepository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		Municipality entity = municipalityRepository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + id));
		municipalityRepository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!municipalityRepository.existsById(id)) {
			throw new ResourceNotFoundException("Municipio no encontrado con ID: " + id);
		}
		municipalityRepository.deleteById(id);
	}

	private MunicipalityResponse toResponse(Municipality entity) {
		return new MunicipalityResponse(entity.getId(), entity.getDepartamentId(), entity.getName(), entity.getStatus());
	}
}
