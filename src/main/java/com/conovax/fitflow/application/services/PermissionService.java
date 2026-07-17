package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.PermissionRequest;
import com.conovax.fitflow.application.dto.response.PermissionResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.pagination.PaginationUtils;
import com.conovax.fitflow.domain.entities.Permission;
import com.conovax.fitflow.domain.exceptions.DuplicateResourceException;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

	private final PermissionRepository permissionRepository;

	@Transactional(readOnly = true)
	public List<PermissionResponse> getAll() {
		return permissionRepository.findAllByStatusTrue().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public PageResponse<PermissionResponse> getAll(Integer page, Integer size, String search) {
		Pageable pageable = PaginationUtils.pageable(page, size);

		Page<Permission> permissionsPage;
		if (search == null || search.isBlank()) {
			permissionsPage = permissionRepository.findAll(pageable);
		} else {
			String q = search.trim();
			permissionsPage = permissionRepository.findBySlugContainingIgnoreCaseOrDescriptionContainingIgnoreCase(q, q, pageable);
		}

		return PaginationUtils.map(permissionsPage, this::toResponse);
	}

	@Transactional(readOnly = true)
	public PageResponse<PermissionResponse> getAllBasicTrue(Integer page, Integer size, String search) {
		Pageable pageable = PaginationUtils.pageable(page, size, Sort.by("description").ascending());

		Page<Permission> permissionsPage;
		if (search == null || search.isBlank()) {
			permissionsPage = permissionRepository.findAllByBasicTrue(pageable);
		} else {
			String q = search.trim();
			permissionsPage = permissionRepository.findByBasicTrueAndSlugContainingIgnoreCaseOrBasicTrueAndDescriptionContainingIgnoreCase(q, q, pageable);
		}

		return PaginationUtils.map(permissionsPage, this::toResponse);
	}

	@Transactional(readOnly = true)
	public PermissionResponse getById(Long id) {
		Permission entity = permissionRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Permiso no encontrado con ID: " + id));
		return toResponse(entity);
	}

	@Transactional
	public PermissionResponse create(PermissionRequest request) {
		if (permissionRepository.existsBySlug(request.slug())) {
			throw new DuplicateResourceException("El permiso ya existe con slug: " + request.slug());
		}

		Boolean basic = request.basic() == null ? Boolean.TRUE : request.basic();
		if (Boolean.FALSE.equals(basic)) {
			assertCurrentUserHasFullAccess();
		}

		Permission entity = Permission.builder()
				.name(request.slug())
				.description(request.description())
				.basic(basic)
				.status(true)
				.build();

		return toResponse(permissionRepository.save(entity));
	}

	@Transactional
	public PermissionResponse update(Long id, PermissionRequest request) {
		Permission entity = permissionRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Permiso no encontrado con ID: " + id));

		if (!entity.getName().equals(request.slug()) && permissionRepository.existsBySlugAndIdNot(request.slug(), id)) {
			throw new DuplicateResourceException("El permiso ya existe con slug: " + request.slug());
		}

		if (request.basic() != null && Boolean.FALSE.equals(request.basic()) && Boolean.TRUE.equals(entity.getBasic())) {
			assertCurrentUserHasFullAccess();
		}

		Permission updated = entity.toBuilder()
				.name(request.slug())
				.description(request.description())
				.basic(request.basic() != null ? request.basic() : entity.getBasic())
				.build();
		return toResponse(permissionRepository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long id) {
		Permission entity = permissionRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Permiso no encontrado con ID: " + id));
		permissionRepository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		Permission entity = permissionRepository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Permiso no encontrado con ID: " + id));
		permissionRepository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!permissionRepository.existsById(id)) {
			throw new ResourceNotFoundException("Permiso no encontrado con ID: " + id);
		}
		permissionRepository.deleteById(id);
	}

	private PermissionResponse toResponse(Permission entity) {
		return new PermissionResponse(entity.getId(), entity.getName(), entity.getDescription(), entity.getBasic(), entity.getStatus());
	}

	private void assertCurrentUserHasFullAccess() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getAuthorities() == null) {
			throw new AccessDeniedException("No tienes permisos para marcar basic en false");
		}

		boolean allowed = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch("FULL_ACCESS"::equals);

		if (!allowed) {
			throw new AccessDeniedException("No tienes permisos para marcar basic en false");
		}
	}
}
