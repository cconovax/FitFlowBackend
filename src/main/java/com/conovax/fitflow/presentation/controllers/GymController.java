package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.GymRequest;
import com.conovax.fitflow.application.dto.request.GymCreateRequest;
import com.conovax.fitflow.application.dto.request.GymUpdateRequest;
import com.conovax.fitflow.application.dto.request.GymBranchRequest;
import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.dto.response.GymResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.services.GymService;
import com.conovax.fitflow.infrastructure.security.annotations.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gyms")
@RequiredArgsConstructor
@Tag(name = "Gyms", description = "CRUD de gimnasios")
@SecurityRequirement(name = "bearerAuth")
public class GymController {

	private final GymService gymService;

	@GetMapping
	@Operation(summary = "Listar gyms")
	@RequirePermission("get:all:gym")
	public ResponseEntity<PageResponse<GymResponse>> getAll(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false) String search
	) {
		return ResponseEntity.ok(gymService.getAll(page, size, search));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener gym por ID")
	@RequirePermission("get:gym")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = GymResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<GymResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(gymService.getById(id));
	}

	@GetMapping("/search")
	@Operation(summary = "Buscar gyms por coincidencia de nombre")
	@RequirePermission("gym:read")
	public ResponseEntity<List<GymResponse>> search(@RequestParam String name) {
		return ResponseEntity.ok(gymService.searchByName(name));
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Crear gym")
	@RequirePermission("create:gym")
	public ResponseEntity<GymResponse> create(
			@Valid @ModelAttribute GymCreateRequest request,
			@RequestPart("logo") MultipartFile logo
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(gymService.create(request, logo));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar gym")
	@RequirePermission("update:gym")
	public ResponseEntity<GymResponse> update(@PathVariable Long id, @Valid @RequestBody GymRequest request) {
		return ResponseEntity.ok(gymService.update(id, request));
	}

	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Actualizar gym (multipart, logo opcional)")
	@RequirePermission("update:gym")
	public ResponseEntity<GymResponse> updateMultipart(
			@PathVariable Long id,
			@Valid @ModelAttribute GymUpdateRequest request,
			@RequestPart(value = "logo", required = false) MultipartFile logo
	) {
		return ResponseEntity.ok(gymService.update(id, request, logo));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar gym (lógico)")
	@RequirePermission("delete:gym")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		gymService.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer gym eliminado")
	@RequirePermission("reset:gym")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		gymService.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar gym (físico)")
	@RequirePermission("force:delete:gym")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		gymService.forceDelete(id);
		return ResponseEntity.noContent().build();
	}

	// ── Sedes ────────────────────────────────────────────────────────────────

	@GetMapping("/{gymId}/branches")
	@Operation(summary = "Listar sedes de un gym")
	@RequirePermission("get:gym")
	public ResponseEntity<List<GymResponse>> getBranches(@PathVariable Long gymId) {
		return ResponseEntity.ok(gymService.getBranches(gymId));
	}

	@PostMapping(value = "/{gymId}/branches", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Crear sede")
	@RequirePermission("create:gym")
	public ResponseEntity<GymResponse> createBranch(
			@PathVariable Long gymId,
			@Valid @ModelAttribute GymBranchRequest request,
			@RequestPart(value = "logo", required = false) MultipartFile logo
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(gymService.createBranch(gymId, request, logo));
	}

	@PutMapping(value = "/{gymId}/branches/{branchId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Actualizar sede")
	@RequirePermission("update:gym")
	public ResponseEntity<GymResponse> updateBranch(
			@PathVariable Long gymId,
			@PathVariable Long branchId,
			@Valid @ModelAttribute GymBranchRequest request,
			@RequestPart(value = "logo", required = false) MultipartFile logo
	) {
		return ResponseEntity.ok(gymService.updateBranch(gymId, branchId, request, logo));
	}

	@DeleteMapping("/{gymId}/branches/{branchId}")
	@Operation(summary = "Eliminar sede (lógico)")
	@RequirePermission("delete:gym")
	public ResponseEntity<Void> deleteBranch(
			@PathVariable Long gymId,
			@PathVariable Long branchId
	) {
		gymService.deleteBranch(gymId, branchId);
		return ResponseEntity.noContent().build();
	}
}
