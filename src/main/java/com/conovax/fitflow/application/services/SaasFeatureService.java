package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.SaasFeatureRequest;
import com.conovax.fitflow.application.dto.response.SaasFeatureResponse;
import com.conovax.fitflow.domain.entities.SaasFeature;
import com.conovax.fitflow.domain.exceptions.DuplicateResourceException;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.SaasFeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaasFeatureService {

	private final SaasFeatureRepository repository;

	@Transactional(readOnly = true)
	public List<SaasFeatureResponse> getAll() {
		return repository.findAllByStatusTrue().stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public SaasFeatureResponse getById(Long id) {
		SaasFeature feature = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Feature SaaS no encontrada con ID: " + id));
		return toResponse(feature);
	}

	@Transactional
	public SaasFeatureResponse create(SaasFeatureRequest request) {
		String code = request.code().trim();
		if (repository.existsByCodeIgnoreCase(code)) {
			throw new DuplicateResourceException("Ya existe una feature SaaS con código: " + code);
		}

		SaasFeature created = repository.save(SaasFeature.builder()
				.code(code)
				.name(request.name().trim())
				.description(request.description())
				.featureType(request.featureType().trim())
				.status(request.status() == null ? Boolean.TRUE : request.status())
				.build());

		return toResponse(created);
	}

	@Transactional
	public SaasFeatureResponse update(Long id, SaasFeatureRequest request) {
		SaasFeature existing = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Feature SaaS no encontrada con ID: " + id));

		String code = request.code().trim();
		if (!existing.getCode().equalsIgnoreCase(code) && repository.existsByCodeIgnoreCaseAndIdNot(code, id)) {
			throw new DuplicateResourceException("Ya existe una feature SaaS con código: " + code);
		}

		SaasFeature updated = existing.toBuilder()
				.code(code)
				.name(request.name().trim())
				.description(request.description())
				.featureType(request.featureType().trim())
				.status(request.status() == null ? existing.getStatus() : request.status())
				.build();

		return toResponse(repository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long id) {
		SaasFeature feature = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Feature SaaS no encontrada con ID: " + id));
		repository.save(feature.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		SaasFeature feature = repository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Feature SaaS no encontrada con ID: " + id));
		repository.save(feature.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Feature SaaS no encontrada con ID: " + id);
		}
		repository.deleteById(id);
	}

	private SaasFeatureResponse toResponse(SaasFeature feature) {
		return new SaasFeatureResponse(
				feature.getId(),
				feature.getCode(),
				feature.getName(),
				feature.getDescription(),
				feature.getFeatureType(),
				feature.getStatus()
		);
	}
}