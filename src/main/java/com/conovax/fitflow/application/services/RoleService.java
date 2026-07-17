package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.RoleRequest;
import com.conovax.fitflow.application.dto.request.TransferPermissionsRequest;
import com.conovax.fitflow.application.dto.response.AuthRoleResponse;
import com.conovax.fitflow.application.dto.response.RoleResponse;
import com.conovax.fitflow.application.dto.response.RoleWithPermissionsResponse;
import com.conovax.fitflow.application.dto.response.UserGymPermissionsResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.pagination.PaginationUtils;
import com.conovax.fitflow.domain.entities.Permission;
import com.conovax.fitflow.domain.entities.PermissionRole;
import com.conovax.fitflow.domain.entities.Role;
import com.conovax.fitflow.domain.exceptions.DuplicateResourceException;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.PermissionRepository;
import com.conovax.fitflow.domain.repositories.PermissionRoleRepository;
import com.conovax.fitflow.domain.repositories.RoleRepository;
import com.conovax.fitflow.domain.repositories.UserRoleRepository;
import com.conovax.fitflow.domain.repositories.UsersGymRepository;
import com.conovax.fitflow.infrastructure.security.GymAuthenticationDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;
	private final PermissionRoleRepository permissionRoleRepository;
	private final UsersGymRepository usersGymRepository;
	private final UserRoleRepository userRoleRepository;

	@Transactional(readOnly = true)
	public PageResponse<RoleResponse> getRoles(Long gymId, Integer page, Integer size) {
		Pageable pageable = PaginationUtils.pageable(page, size);

		Page<Role> roles = (gymId != null)
				? roleRepository.findAllByGymId(gymId, pageable)
				: roleRepository.findAll(pageable);

		return PaginationUtils.map(roles, this::toResponse);
	}

	@Transactional(readOnly = true)
	public List<RoleResponse> getRolesForGym(Long gymId) {
		List<Role> roles = roleRepository.findAllByGymIdOrUniversal(gymId);
		return roles.stream()
				.filter(r -> !Boolean.TRUE.equals(r.getFullAccess()))
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<RoleWithPermissionsResponse> getRolesForGymWithPermissions(Long gymId) {
		List<Role> roles = roleRepository.findAllByGymIdOrUniversal(gymId);
		return roles.stream()
				.filter(r -> !Boolean.TRUE.equals(r.getFullAccess()))
				.map(this::toWithPermissionsResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public UserGymPermissionsResponse getRolesAndPermissionsForUserGym(Long userGymId) {
		var userGym = usersGymRepository.findByIdAndStatusTrue(userGymId)
				.orElseThrow(() -> new ResourceNotFoundException("Relacion user_gym no encontrada con ID: " + userGymId));

		List<Long> roleIds = userRoleRepository.findRoleIdsByUserGymId(userGym.getId());
		if (roleIds.isEmpty()) {
			return new UserGymPermissionsResponse(List.of(), List.of());
		}

		List<Role> roles = roleRepository.findAllById(roleIds).stream()
				.filter(role -> Boolean.TRUE.equals(role.getStatus()))
				.toList();

		List<AuthRoleResponse> roleResponses = roles.stream()
				.map(role -> new AuthRoleResponse(role.getId(), role.getName(), role.getFullAccess()))
				.toList();

		boolean hasFullAccess = roles.stream()
				.anyMatch(role -> Boolean.TRUE.equals(role.getFullAccess()));

		Map<String, String> permissionMap = roles.stream()
				.flatMap(role -> role.getPermissions() == null
						? java.util.stream.Stream.<Permission>empty()
						: role.getPermissions().stream())
				.filter(permission -> Boolean.TRUE.equals(permission.getStatus()))
				.map(Permission::getName)
				.filter(name -> name != null && !name.isBlank())
				.collect(Collectors.toMap(p -> p, p -> p, (a, b) -> a));

		if (hasFullAccess) {
			permissionMap.put("FULL_ACCESS", "FULL_ACCESS");
		}

		List<String> permissions = permissionMap.values().stream()
				.sorted()
				.toList();

		return new UserGymPermissionsResponse(roleResponses, permissions);
	}

	@Transactional(readOnly = true)
	public RoleResponse getRoleById(Long id) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + id));
		return toResponse(role);
	}

	@Transactional(readOnly = true)
	public RoleWithPermissionsResponse getRoleByIdWithPermissions(Long id) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + id));
		return toWithPermissionsResponse(role);
	}

	@Transactional(readOnly = true)
	public RoleResponse getRoleByName(String name) {
		Role role = roleRepository.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con nombre: " + name));
		return toResponse(role);
	}

	@Transactional
	public RoleResponse createRole(RoleRequest request) {
		if (Boolean.TRUE.equals(request.fullAccess())) {
			assertCurrentUserHasFullAccess();
		}

		if (roleRepository.existsByName(request.name())) {
			throw new DuplicateResourceException("El rol ya existe con nombre: " + request.name());
		}

		Role role = Role.builder()
				.name(request.name())
				.code(request.code())
				.gymId(request.gymId())
				.fullAccess(request.fullAccess() != null ? request.fullAccess() : false)
				.isStaff(request.isStaff() != null ? request.isStaff() : false)
				.status(request.status() != null ? request.status() : true)
				.build();

		Role saved = roleRepository.save(role);

		if (request.permissionIds() != null && !request.permissionIds().isEmpty()) {
			List<Long> permissionIds = request.permissionIds().stream()
					.filter(Objects::nonNull)
					.distinct()
					.toList();

			for (Long permissionId : permissionIds) {
				permissionRepository.findByIdAndStatusTrue(permissionId)
						.orElseThrow(() -> new ResourceNotFoundException("Permiso no encontrado con ID: " + permissionId));

				if (!permissionRoleRepository.existsByRoleIdAndPermissionId(saved.getId(), permissionId)) {
					permissionRoleRepository.save(PermissionRole.builder()
							.permissionId(permissionId)
							.roleId(saved.getId())
							.build());
				}
			}
		}

		return toResponse(saved);
	}

	@Transactional
	public RoleResponse updateRole(Long id, RoleRequest request) {
		if (Boolean.TRUE.equals(request.fullAccess())) {
			assertCurrentUserHasFullAccess();
		}

		Role existing = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + id));

		if (!existing.getName().equals(request.name()) && roleRepository.existsByName(request.name())) {
			throw new DuplicateResourceException("El rol ya existe con nombre: " + request.name());
		}

		Role updated = existing.toBuilder()
				.name(request.name())
				.code(request.code())
				.gymId(request.gymId())
				.fullAccess(request.fullAccess() != null ? request.fullAccess() : false)
				.isStaff(request.isStaff() != null ? request.isStaff() : false)
				.status(request.status() != null ? request.status() : true)
				.build();

		Role saved = roleRepository.save(updated);

		List<Long> permissionIds = (request.permissionIds() == null)
				? List.of()
				: request.permissionIds().stream()
						.filter(Objects::nonNull)
						.distinct()
						.toList();

		permissionRoleRepository.deleteByRoleId(saved.getId());

		for (Long permissionId : permissionIds) {
			permissionRepository.findByIdAndStatusTrue(permissionId)
					.orElseThrow(() -> new ResourceNotFoundException("Permiso no encontrado con ID: " + permissionId));

			permissionRoleRepository.save(PermissionRole.builder()
					.permissionId(permissionId)
					.roleId(saved.getId())
					.build());
		}

		return toResponse(saved);
	}

	@Transactional
	public void deleteRole(Long id) {
		Role existing = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + id));

		assertCanDeleteRole(existing);

		Role deleted = existing.toBuilder()
				.status(false)
				.build();

		roleRepository.save(deleted);
	}

	@Transactional
	public void forceDeleteRole(Long id) {
		Role existing = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + id));

		assertCanDeleteRole(existing);

		permissionRoleRepository.deleteByRoleId(id);
		roleRepository.deleteById(id);
	}

	@Transactional
	public RoleWithPermissionsResponse transferPermissions(TransferPermissionsRequest request) {
		Role source = roleRepository.findById(request.sourceRoleId())
				.orElseThrow(() -> new ResourceNotFoundException("Rol origen no encontrado con ID: " + request.sourceRoleId()));

		Role target = roleRepository.findById(request.targetRoleId())
				.orElseThrow(() -> new ResourceNotFoundException("Rol destino no encontrado con ID: " + request.targetRoleId()));

		List<Long> sourcePermissionIds = source.getPermissions().stream()
				.filter(p -> Boolean.TRUE.equals(p.getStatus()))
				.map(Permission::getId)
				.filter(Objects::nonNull)
				.distinct()
				.toList();

		for (Long permissionId : sourcePermissionIds) {
			if (!permissionRoleRepository.existsByRoleIdAndPermissionId(target.getId(), permissionId)) {
				permissionRoleRepository.save(PermissionRole.builder()
						.permissionId(permissionId)
						.roleId(target.getId())
						.build());
			}
		}

		Role refreshed = roleRepository.findById(target.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Rol destino no encontrado con ID: " + target.getId()));
		return toWithPermissionsResponse(refreshed);
	}

	@Transactional
	public void resetRole(Long id) {
		Role existing = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + id));

		assertCanDeleteRole(existing);

		Role reset = existing.toBuilder()
				.status(true)
				.build();

		roleRepository.save(reset);
	}

	private RoleResponse toResponse(Role role) {
		return new RoleResponse(
				role.getId(),
				role.getName(),
				role.getCode(),
				role.getGymId(),
				role.getFullAccess(),
				role.getIsStaff(),
				role.getStatus()
		);
	}

	private RoleWithPermissionsResponse toWithPermissionsResponse(Role role) {
		List<Long> permissionIds = role.getPermissions().stream()
				.map(p -> p.getId())
				.filter(Objects::nonNull)
				.distinct()
				.sorted()
				.toList();

		return new RoleWithPermissionsResponse(
				role.getId(),
				role.getName(),
				role.getCode(),
				role.getGymId(),
				role.getFullAccess(),
				role.getIsStaff(),
				role.getStatus(),
				permissionIds
		);
	}

	private void assertCurrentUserHasFullAccess() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getAuthorities() == null) {
			throw new AccessDeniedException("No tienes permisos para asignar full_access");
		}

		boolean allowed = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch("FULL_ACCESS"::equals);

		if (!allowed) {
			throw new AccessDeniedException("No tienes permisos para asignar full_access");
		}
	}

	private boolean currentUserHasFullAccess() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getAuthorities() == null) {
			return false;
		}
		return authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch("FULL_ACCESS"::equals);
	}

	private void assertCanDeleteRole(Role role) {
		if (currentUserHasFullAccess()) {
			return;
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new AccessDeniedException("No tienes permisos para eliminar este rol");
		}

		Long roleGymId = role.getGymId();
		if (roleGymId == null) {
			throw new AccessDeniedException("No tienes permisos para eliminar un rol universal");
		}

		Long selectedGymId = null;
		Object details = authentication.getDetails();
		if (details instanceof GymAuthenticationDetails gymDetails) {
			selectedGymId = gymDetails.gymId();
		}

		if (selectedGymId == null || !selectedGymId.equals(roleGymId)) {
			throw new AccessDeniedException("No tienes permisos para eliminar un rol que no pertenece a tu gimnasio");
		}
	}
}
