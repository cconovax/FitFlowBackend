package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.BenefitRequest;
import com.conovax.fitflow.application.dto.response.BenefitResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.pagination.PaginationUtils;
import com.conovax.fitflow.domain.entities.Benefit;
import com.conovax.fitflow.domain.exceptions.DuplicateResourceException;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.BenefitRepository;
import com.conovax.fitflow.domain.repositories.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BenefitService {

	private final BenefitRepository repository;
	private final GymRepository gymRepository;

	@Transactional(readOnly = true)
	public List<BenefitResponse> getAll() {
		return repository.findAllByStatusTrue().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public List<BenefitResponse> getAllByGymOrGlobal(Long gymId) {
		validateGymIfPresent(gymId);
		return repository.findAllActiveByGymIdOrGlobal(gymId).stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public PageResponse<BenefitResponse> getAllByGymOrGlobal(Long gymId, Integer page, Integer size, String search) {
		validateGymIfPresent(gymId);
		Pageable pageable = PaginationUtils.pageable(page, size);
		Page<Benefit> result = repository.findAllActiveByGymIdOrGlobalAndSearchFilter(gymId, search, pageable);
		return PaginationUtils.map(result, this::toResponse);
	}

	@Transactional(readOnly = true)
	public BenefitResponse getById(Long id) {
		Benefit entity = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Beneficio no encontrado con ID: " + id));
		return toResponse(entity);
	}

	@Transactional
	public BenefitResponse create(BenefitRequest request) {
		validateGymIfPresent(request.gymId());

		if (repository.existsByNameIgnoreCaseAndGymIdAndStatusTrue(request.name(), request.gymId())) {
			throw new DuplicateResourceException("Ya existe un beneficio con ese nombre");
		}

		Benefit entity = Benefit.builder()
				.name(request.name())
				.description(request.description())
				.gymId(request.gymId())
				.status(true)
				.build();

		return toResponse(repository.save(entity));
	}

	@Transactional
	public BenefitResponse update(Long id, BenefitRequest request) {
		Benefit entity = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Beneficio no encontrado con ID: " + id));

		validateGymIfPresent(request.gymId());

		if (repository.existsByNameIgnoreCaseAndGymIdAndIdNotAndStatusTrue(request.name(), request.gymId(), id)) {
			throw new DuplicateResourceException("Ya existe un beneficio con ese nombre");
		}

		Benefit updated = entity.toBuilder()
				.name(request.name())
				.description(request.description())
				.gymId(request.gymId())
				.build();

		return toResponse(repository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long id) {
		Benefit entity = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Beneficio no encontrado con ID: " + id));
		repository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		Benefit entity = repository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Beneficio no encontrado con ID: " + id));
		repository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Beneficio no encontrado con ID: " + id);
		}
		repository.deleteById(id);
	}

	private void validateGymIfPresent(Long gymId) {
		if (gymId == null) {
			return;
		}

		gymRepository.findByIdAndStatusTrue(gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + gymId));
	}

	private BenefitResponse toResponse(Benefit entity) {
		return new BenefitResponse(
				entity.getId(),
				entity.getName(),
				entity.getDescription(),
				entity.getStatus(),
				entity.getGymId()
		);
	}
}
