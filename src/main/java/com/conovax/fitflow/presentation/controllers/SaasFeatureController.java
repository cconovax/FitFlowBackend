package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.SaasFeatureRequest;
import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.dto.response.SaasFeatureResponse;
import com.conovax.fitflow.application.services.SaasFeatureService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saas/features")
@RequiredArgsConstructor
@Tag(name = "SaaS Features", description = "CRUD de features de planes SaaS")
@SecurityRequirement(name = "bearerAuth")
public class SaasFeatureController {

	private final SaasFeatureService service;

	@GetMapping
	@Operation(summary = "Listar features SaaS")
	@RequirePermission("get:all:features:saas")
	public ResponseEntity<List<SaasFeatureResponse>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener feature SaaS por ID")
	@RequirePermission("get:features:saas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = SaasFeatureResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<SaasFeatureResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@PostMapping
	@Operation(summary = "Crear feature SaaS")
	@RequirePermission("create:features:saas")
	public ResponseEntity<SaasFeatureResponse> create(@Valid @RequestBody SaasFeatureRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar feature SaaS")
	@RequirePermission("update:features:saas")
	public ResponseEntity<SaasFeatureResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody SaasFeatureRequest request
	) {
		return ResponseEntity.ok(service.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar feature SaaS (lógico)")
	@RequirePermission("delete:features:saas")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		service.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer feature SaaS eliminada")
	@RequirePermission("reset:features:saas")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		service.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar feature SaaS (físico)")
	@RequirePermission("force:delete:features:saas")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		service.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
