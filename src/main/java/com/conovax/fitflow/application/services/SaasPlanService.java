package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.SaasPlanFeatureRequest;
import com.conovax.fitflow.application.dto.request.SaasPlanRequest;
import com.conovax.fitflow.application.dto.response.SaasFeatureResponse;
import com.conovax.fitflow.application.dto.response.SaasPlanFeatureResponse;
import com.conovax.fitflow.application.dto.response.SaasPlanResponse;
import com.conovax.fitflow.domain.entities.SaasFeature;
import com.conovax.fitflow.domain.entities.SaasPlan;
import com.conovax.fitflow.domain.entities.SaasPlanFeature;
import com.conovax.fitflow.domain.exceptions.DuplicateResourceException;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.SaasFeatureRepository;
import com.conovax.fitflow.domain.repositories.SaasPlanFeatureRepository;
import com.conovax.fitflow.domain.repositories.SaasPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SaasPlanService {

	private final SaasPlanRepository repository;
	private final SaasPlanFeatureRepository planFeatureRepository;
	private final SaasFeatureRepository featureRepository;

	@Transactional(readOnly = true)
	public List<SaasPlanResponse> getAll() {
		return repository.findAllByStatusTrue().stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public SaasPlanResponse getById(Long id) {
		SaasPlan plan = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Plan SaaS no encontrado con ID: " + id));
		return toResponse(plan);
	}

	@Transactional
	public SaasPlanResponse create(SaasPlanRequest request) {
		String code = request.code().trim();
		if (repository.existsByCodeIgnoreCase(code)) {
			throw new DuplicateResourceException("Ya existe un plan SaaS con código: " + code);
		}

		SaasPlan created = repository.save(SaasPlan.builder()
				.code(code)
				.name(request.name().trim())
				.description(request.description())
				.price(request.price())
				.numDays(request.numDays())
				.stripePriceId(request.stripePriceId())
				.status(request.status() == null ? Boolean.TRUE : request.status())
				.build());

		persistFeatures(created.getId(), request.features());
		return toResponse(created);
	}

	@Transactional
	public SaasPlanResponse update(Long id, SaasPlanRequest request) {
		SaasPlan existing = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Plan SaaS no encontrado con ID: " + id));

		String code = request.code().trim();
		if (!existing.getCode().equalsIgnoreCase(code) && repository.existsByCodeIgnoreCaseAndIdNot(code, id)) {
			throw new DuplicateResourceException("Ya existe un plan SaaS con código: " + code);
		}

		SaasPlan updated = existing.toBuilder()
				.code(code)
				.name(request.name().trim())
				.description(request.description())
				.price(request.price())
				.numDays(request.numDays())
				.stripePriceId(request.stripePriceId())
				.status(request.status() == null ? existing.getStatus() : request.status())
				.build();

		SaasPlan saved = repository.save(updated);
		planFeatureRepository.deleteBySaasPlanId(saved.getId());
		persistFeatures(saved.getId(), request.features());
		return toResponse(saved);
	}

	@Transactional
	public void deleteLogical(Long id) {
		SaasPlan plan = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Plan SaaS no encontrado con ID: " + id));
		repository.save(plan.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		SaasPlan plan = repository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Plan SaaS no encontrado con ID: " + id));
		repository.save(plan.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Plan SaaS no encontrado con ID: " + id);
		}
		planFeatureRepository.deleteBySaasPlanId(id);
		repository.deleteById(id);
	}

	private void persistFeatures(Long saasPlanId, List<SaasPlanFeatureRequest> featureRequests) {
		if (saasPlanId == null || featureRequests == null || featureRequests.isEmpty()) {
			return;
		}

		Map<Long, SaasPlanFeatureRequest> distinctByFeatureId = new LinkedHashMap<>();
		for (SaasPlanFeatureRequest featureRequest : featureRequests) {
			if (featureRequest == null || featureRequest.saasFeatureId() == null) {
				continue;
			}
			distinctByFeatureId.put(featureRequest.saasFeatureId(), featureRequest);
		}

		List<SaasPlanFeature> relations = distinctByFeatureId.values().stream()
				.map(featureRequest -> {
					featureRepository.findByIdAndStatusTrue(featureRequest.saasFeatureId())
							.orElseThrow(() -> new ResourceNotFoundException(
									"Feature SaaS no encontrada con ID: " + featureRequest.saasFeatureId()
							));

					return SaasPlanFeature.builder()
							.saasPlanId(saasPlanId)
							.saasFeatureId(featureRequest.saasFeatureId())
							.valueText(featureRequest.valueText())
							.valueNumber(featureRequest.valueNumber())
							.valueBoolean(featureRequest.valueBoolean())
							.build();
				})
				.toList();

		if (!relations.isEmpty()) {
			planFeatureRepository.saveAll(relations);
		}
	}

	private SaasPlanResponse toResponse(SaasPlan plan) {
		List<SaasPlanFeature> planFeatures = plan.getId() == null
				? List.of()
				: planFeatureRepository.findAllBySaasPlanId(plan.getId());

		List<Long> featureIds = planFeatures.stream()
				.map(SaasPlanFeature::getSaasFeatureId)
				.filter(java.util.Objects::nonNull)
				.distinct()
				.toList();

		Map<Long, SaasFeature> featuresById = featureIds.isEmpty()
				? Map.of()
				: featureRepository.findAllById(featureIds).stream()
						.collect(java.util.stream.Collectors.toMap(SaasFeature::getId, feature -> feature));

		List<SaasPlanFeatureResponse> featureResponses = planFeatures.stream()
				.map(planFeature -> {
					SaasFeature feature = featuresById.get(planFeature.getSaasFeatureId());
					return new SaasPlanFeatureResponse(
							planFeature.getId(),
							planFeature.getSaasFeatureId(),
							feature != null ? feature.getCode() : null,
							feature != null ? feature.getName() : null,
							planFeature.getValueText(),
							planFeature.getValueNumber(),
							planFeature.getValueBoolean()
					);
				})
				.toList();

		return new SaasPlanResponse(
				plan.getId(),
				plan.getCode(),
				plan.getName(),
				plan.getDescription(),
				plan.getPrice(),
				plan.getNumDays(),
				plan.getStatus(),
				plan.getStripePriceId(),
				featureResponses
		);
	}

	@Transactional(readOnly = true)
	public List<SaasFeatureResponse> getFeaturesForPlan(Long saasPlanId) {
		return getById(saasPlanId).features().stream()
				.map(feature -> new SaasFeatureResponse(
						feature.saasFeatureId(),
						feature.featureCode(),
						feature.featureName(),
						null,
						null,
						true
				))
				.toList();
	}
}