package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.GymRequest;
import com.conovax.fitflow.application.dto.request.GymCreateRequest;
import com.conovax.fitflow.application.dto.request.GymUpdateRequest;
import com.conovax.fitflow.application.dto.request.GymBranchRequest;
import com.conovax.fitflow.application.dto.response.GymResponse;
import com.conovax.fitflow.application.dto.response.GymSubscriptionResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.pagination.PaginationUtils;
import com.conovax.fitflow.domain.entities.Gym;
import com.conovax.fitflow.domain.entities.GymStatus;
import com.conovax.fitflow.domain.entities.Municipality;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.GymRepository;
import com.conovax.fitflow.domain.repositories.MunicipalityRepository;
import com.conovax.fitflow.infrastructure.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GymService {

	private final GymRepository gymRepository;
	private final MunicipalityRepository municipalityRepository;
	private final FileStorageService fileStorageService;
	private final GymSubscriptionService gymSubscriptionService;

	@Transactional(readOnly = true)
	public PageResponse<GymResponse> getAll(Integer page, Integer size, String search) {
		Pageable pageable = PaginationUtils.pageable(page, size);

		Page<Gym> gyms;
		if (search == null || search.isBlank()) {
			gyms = gymRepository.findAll(pageable);
		} else {
			String q = search.trim();
			gyms = gymRepository.findByNameContainingIgnoreCaseOrNitContainingIgnoreCase(q, q, pageable);
		}

		return PaginationUtils.map(gyms, this::toResponse);
	}

	@Transactional(readOnly = true)
	public GymResponse getById(Long id) {
		Gym entity = gymRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + id));
		return toResponse(entity);
	}

	@Transactional(readOnly = true)
	public List<GymResponse> searchByName(String name) {
		return gymRepository.findByNameContainingIgnoreCase(name)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional
	public GymResponse create(GymCreateRequest request, MultipartFile logo) {
		String logoUrl = fileStorageService.storeGymLogo(logo);

		Gym entity = Gym.builder()
				.name(request.getName())
				.nit(request.getNit())
				.logo(logoUrl)
				.municipalitieId(request.getMunicipalitieId())
				.status(true)
				.phone(request.getPhone())
				.email(request.getEmail())
				.build();
		return toResponse(gymRepository.save(entity));
	}

	@Transactional
	public GymResponse update(Long id, GymRequest request) {
		Gym entity = gymRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + id));
		String logoUrl = entity.getLogo();
		if (request.logo() != null && !request.logo().isBlank()) {
			logoUrl = request.logo();
		}
		Gym updated = entity.toBuilder()
				.name(request.name())
				.nit(request.nit())
				.logo(logoUrl)
				.municipalitieId(request.municipalitieId())
				.phone(request.phone())
				.email(request.email())
				.build();
		return toResponse(gymRepository.save(updated));
	}

	@Transactional
	public GymResponse update(Long id, GymUpdateRequest request, MultipartFile logo) {
		Gym entity = gymRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + id));
		String logoUrl = entity.getLogo();
		if (logo != null && !logo.isEmpty()) {
			logoUrl = fileStorageService.storeGymLogo(logo);
		}

		Gym updated = entity.toBuilder()
				.name(request.getName())
				.nit(request.getNit())
				.municipalitieId(request.getMunicipalitieId())
				.phone(request.getPhone())
				.email(request.getEmail())
				.logo(logoUrl)
				.build();

		return toResponse(gymRepository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long id) {
		Gym entity = gymRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + id));
		gymRepository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		Gym entity = gymRepository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + id));
		gymRepository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!gymRepository.existsById(id)) {
			throw new ResourceNotFoundException("Gym no encontrado con ID: " + id);
		}
		gymRepository.deleteById(id);
	}

	// ── Sedes ──────────────────────────────────────────────────────────────────

	@Transactional(readOnly = true)
	public List<GymResponse> getBranches(Long parentGymId) {
		if (!gymRepository.existsById(parentGymId)) {
			throw new ResourceNotFoundException("Gym no encontrado con ID: " + parentGymId);
		}
		return gymRepository.findByParentGymId(parentGymId)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional
	public GymResponse createBranch(Long parentGymId, GymBranchRequest request, MultipartFile logo) {
		Gym parent = gymRepository.findByIdAndStatusTrue(parentGymId)
				.orElseThrow(() -> new ResourceNotFoundException("Gym padre no encontrado con ID: " + parentGymId));
		String logoUrl = (logo != null && !logo.isEmpty())
				? fileStorageService.storeGymLogo(logo)
				: parent.getLogo(); // hereda logo del padre si no se sube uno
		Gym branch = Gym.builder()
				.name(request.getName())
				.nit(parent.getNit())   // hereda NIT del padre
				.logo(logoUrl)
				.municipalitieId(request.getMunicipalitieId())
				.status(true)
				.phone(request.getPhone())
				.email(request.getEmail())
				.parentGymId(parentGymId)
				.build();
		return toResponse(gymRepository.save(branch));
	}

	@Transactional
	public GymResponse updateBranch(Long parentGymId, Long branchId, GymBranchRequest request, MultipartFile logo) {
		Gym branch = gymRepository.findByIdAndStatusTrue(branchId)
				.orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con ID: " + branchId));
		if (!parentGymId.equals(branch.getParentGymId())) {
			throw new ResourceNotFoundException("La sede no pertenece al gym con ID: " + parentGymId);
		}
		String logoUrl = branch.getLogo();
		if (logo != null && !logo.isEmpty()) {
			logoUrl = fileStorageService.storeGymLogo(logo);
		}
		Gym updated = branch.toBuilder()
				.name(request.getName())
				.municipalitieId(request.getMunicipalitieId())
				.phone(request.getPhone())
				.email(request.getEmail())
				.logo(logoUrl)
				.build();
		return toResponse(gymRepository.save(updated));
	}

	@Transactional
	public void deleteBranch(Long parentGymId, Long branchId) {
		Gym branch = gymRepository.findByIdAndStatusTrue(branchId)
				.orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con ID: " + branchId));
		if (!parentGymId.equals(branch.getParentGymId())) {
			throw new ResourceNotFoundException("La sede no pertenece al gym con ID: " + parentGymId);
		}
		gymRepository.save(branch.toBuilder().status(false).build());
	}

	private GymResponse toResponse(Gym entity) {
		String municipalitieName = resolveMunicipalityName(entity.getMunicipalitieId());
		GymSubscriptionResponse subscription = gymSubscriptionService.getCurrentByGymId(entity.getId());
		return new GymResponse(
				entity.getId(),
				entity.getName(),
				entity.getNit(),
				entity.getLogo(),
				entity.getMunicipalitieId(),
				municipalitieName,
				entity.getStatus(),
				entity.getPhone(),
				entity.getEmail(),
				subscription.active(),
				subscription.planName(),
				subscription.startDate(),
				subscription.endDate(),
				entity.getParentGymId(),
				entity.getGymStatus() != null ? entity.getGymStatus().name() : GymStatus.ACTIVE.name(),
				entity.getTrialExpiresAt()
		);
	}

	private String resolveMunicipalityName(Long municipalitieId) {
		if (municipalitieId == null) {
			return null;
		}
		return municipalityRepository.findByIdAndStatusTrue(municipalitieId)
				.map(Municipality::getName)
				.or(() -> municipalityRepository.findByIdAndStatusFalse(municipalitieId)
						.map(Municipality::getName))
				.orElse(null);
	}
}
