package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.PermissionRequest;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.dto.response.PermissionResponse;
import com.conovax.sexbody.application.pagination.PageResponse;
import com.conovax.sexbody.application.services.PermissionService;
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

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@Tag(name = "Permissions", description = "CRUD de permisos")
@SecurityRequirement(name = "bearerAuth")
public class PermissionController {

	private final PermissionService permissionService;

	@GetMapping
	@Operation(summary = "Listar permisos (paginado)")
	@RequirePermission("get:all:permission")
	public ResponseEntity<PageResponse<PermissionResponse>> getAll(
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "15") Integer size,
			@RequestParam(required = false) String search
	) {
		return ResponseEntity.ok(permissionService.getAll(page, size, search));
	}

	@GetMapping("/basic")
	@Operation(summary = "Listar permisos básicos (basic=true) (paginado)")
	@RequirePermission("get:all:permission")
	public ResponseEntity<PageResponse<PermissionResponse>> getAllBasicTrue(
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "15") Integer size,
			@RequestParam(required = false) String search
	) {
		return ResponseEntity.ok(permissionService.getAllBasicTrue(page, size, search));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener permiso por ID")
	@RequirePermission("get:permission")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = PermissionResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<PermissionResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(permissionService.getById(id));
	}

	@PostMapping
	@Operation(summary = "Crear permiso")
	@RequirePermission("create:permission")
	public ResponseEntity<PermissionResponse> create(@Valid @RequestBody PermissionRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar permiso")
	@RequirePermission("update:permission")
	public ResponseEntity<PermissionResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody PermissionRequest request
	) {
		return ResponseEntity.ok(permissionService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar permiso (lógico)")
	@RequirePermission("delete:permission")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		permissionService.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer permiso eliminado")
	@RequirePermission("reset:permission")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		permissionService.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar permiso (físico)")
	@RequirePermission("force:delete:permission")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		permissionService.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
