package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.SaasPlanRequest;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.dto.response.SaasPlanResponse;
import com.conovax.sexbody.application.services.SaasPlanService;
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
@RequestMapping("/api/v1/saas/plans")
@RequiredArgsConstructor
@Tag(name = "SaaS Plans", description = "CRUD de planes SaaS")
@SecurityRequirement(name = "bearerAuth")
public class SaasPlanController {

	private final SaasPlanService service;

	@GetMapping
	@Operation(summary = "Listar planes SaaS")
	@RequirePermission("get:all:plans:saas")
	public ResponseEntity<List<SaasPlanResponse>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener plan SaaS por ID")
	@RequirePermission("get:plans:saas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = SaasPlanResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<SaasPlanResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@PostMapping
	@Operation(summary = "Crear plan SaaS")
	@RequirePermission("create:plans:saas")
	public ResponseEntity<SaasPlanResponse> create(@Valid @RequestBody SaasPlanRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar plan SaaS")
	@RequirePermission("update:plans:saas")
	public ResponseEntity<SaasPlanResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody SaasPlanRequest request
	) {
		return ResponseEntity.ok(service.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar plan SaaS (lógico)")
	@RequirePermission("delete:plans:saas")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		service.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer plan SaaS eliminado")
	@RequirePermission("reset:plans:saas")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		service.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar plan SaaS (físico)")
	@RequirePermission("force:delete:plan:saas")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		service.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
