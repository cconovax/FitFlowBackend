package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.RoleRequest;
import com.conovax.sexbody.application.dto.request.TransferPermissionsRequest;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.dto.response.RoleResponse;
import com.conovax.sexbody.application.dto.response.RoleWithPermissionsResponse;
import com.conovax.sexbody.application.dto.response.UserGymPermissionsResponse;
import com.conovax.sexbody.application.pagination.PageResponse;
import com.conovax.sexbody.application.services.RoleService;
import com.conovax.sexbody.infrastructure.security.annotations.RequireActiveGymSubscription;
import com.conovax.sexbody.infrastructure.security.annotations.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Endpoints para gestión de roles")
@SecurityRequirement(name = "bearerAuth")
public class RoleController {

	private final RoleService roleService;

	@GetMapping
	@RequirePermission("get:all:roles")
	@RequireActiveGymSubscription
	@Operation(summary = "Listar roles", description = "Lista todos los roles o filtra por gymId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de roles",
					content = @Content(schema = @Schema(implementation = RoleResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<PageResponse<RoleResponse>> getRoles(
			@RequestParam(required = false) Long gymId,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "15") Integer size
	) {
		return ResponseEntity.ok(roleService.getRoles(gymId, page, size));
	}

	@GetMapping("/gym/{gymId}")
	@RequirePermission("get:all:roles:gym")
	@RequireActiveGymSubscription
	@Operation(summary = "Listar roles para un gimnasio", description = "Lista roles asociados al gymId + roles universales (gymId null)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de roles",
					content = @Content(schema = @Schema(implementation = RoleWithPermissionsResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<List<RoleWithPermissionsResponse>> getRolesForGym(@PathVariable Long gymId) {
		return ResponseEntity.ok(roleService.getRolesForGymWithPermissions(gymId));
	}

	@GetMapping("/user/{userGymId}/permissions")
	@RequirePermission("get:roles:and:permissions:user:gym")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	@Operation(summary = "Listar roles y permisos de un user_gym", description = "Obtiene roles y permisos segun roles asignados al user_gym")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Roles y permisos",
					content = @Content(schema = @Schema(implementation = UserGymPermissionsResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<UserGymPermissionsResponse> getRolesAndPermissionsForUserGym(
			@PathVariable Long userGymId
	) {
		return ResponseEntity.ok(roleService.getRolesAndPermissionsForUserGym(userGymId));
	}

	@GetMapping("/{id}")
	@RequirePermission("get:role:gym")
	@Operation(summary = "Obtener rol por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rol encontrado",
					content = @Content(schema = @Schema(implementation = RoleWithPermissionsResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<RoleWithPermissionsResponse> getRoleById(@PathVariable Long id) {
		return ResponseEntity.ok(roleService.getRoleByIdWithPermissions(id));
	}

	@GetMapping("/search")
	@RequirePermission("search:role:for:name")
	@Operation(summary = "Buscar rol por nombre")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rol encontrado",
					content = @Content(schema = @Schema(implementation = RoleResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<RoleResponse> getRoleByName(@RequestParam String name) {
		return ResponseEntity.ok(roleService.getRoleByName(name));
	}

	@PostMapping
	@RequirePermission("create:role:gym")
	@RequireActiveGymSubscription
	@Operation(summary = "Crear rol")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Rol creado",
					content = @Content(schema = @Schema(implementation = RoleResponse.class))),
			@ApiResponse(responseCode = "409", description = "Duplicado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest request) {
		RoleResponse created = roleService.createRole(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	@RequirePermission("update:role:gym")
	@RequireActiveGymSubscription
	@Operation(summary = "Actualizar rol")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rol actualizado",
					content = @Content(schema = @Schema(implementation = RoleResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "409", description = "Duplicado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
		return ResponseEntity.ok(roleService.updateRole(id, request));
	}

	@DeleteMapping("/{id}")
	@RequirePermission("delete:role:gym")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	@Operation(summary = "Eliminar rol (lógico)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Eliminado"),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@RequirePermission("force:delete:role:gym")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	@Operation(summary = "Eliminar rol (físico)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Eliminado"),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<Void> forceDeleteRole(@PathVariable Long id) {
		roleService.forceDeleteRole(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/transfer-permissions")
	@RequirePermission("transfer:permissions:role")
	@RequireActiveGymSubscription
	@Operation(summary = "Transferir permisos entre roles", description = "Agrega los permisos del rol origen al rol destino sin eliminar los que ya tiene el destino")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Permisos transferidos",
					content = @Content(schema = @Schema(implementation = RoleWithPermissionsResponse.class))),
			@ApiResponse(responseCode = "404", description = "Rol no encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<RoleWithPermissionsResponse> transferPermissions(
			@Valid @RequestBody TransferPermissionsRequest request
	) {
		return ResponseEntity.ok(roleService.transferPermissions(request));
	}

	@PutMapping("/{id}/reset")
	@RequirePermission("reset:role:gym")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	@Operation(summary = "Restablecer rol")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Restablecido"),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<Void> resetRole(@PathVariable Long id) {
		roleService.resetRole(id);
		return ResponseEntity.noContent().build();
	}
}
