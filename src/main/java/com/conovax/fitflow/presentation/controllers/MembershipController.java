package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.MembershipRequest;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.dto.response.MembershipResponse;
import com.conovax.sexbody.application.dto.response.MembershipWithBenefitsResponse;
import com.conovax.sexbody.application.services.MembershipService;
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
@RequestMapping("/api/v1/memberships")
@RequiredArgsConstructor
@Tag(name = "Memberships", description = "CRUD de membresías")
@SecurityRequirement(name = "bearerAuth")
public class MembershipController {

	private final MembershipService service;

	@GetMapping
	@Operation(summary = "Listar membresías")
	@RequirePermission("get:all:membership")
	public ResponseEntity<List<MembershipWithBenefitsResponse>> getAll() {
		return ResponseEntity.ok(service.getAllWithBenefitsAndPrice());
	}

	@GetMapping("/gym/{gymId}")
	@Operation(summary = "Listar membresías por gym con beneficios")
	@RequirePermission("get:membership:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<List<MembershipWithBenefitsResponse>> getAllByGymWithBenefits(@PathVariable Long gymId) {
		return ResponseEntity.ok(service.getAllByGymIdWithBenefits(gymId));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener membresía por ID")
	@RequirePermission("get:membership")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = MembershipResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<MembershipResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@PostMapping
	@Operation(summary = "Crear membresía")
	@RequirePermission("createmembership:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<MembershipResponse> create(@Valid @RequestBody MembershipRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar membresía")
	@RequirePermission("membership:gym:update")
	@RequireActiveGymSubscription
	public ResponseEntity<MembershipResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody MembershipRequest request
	) {
		return ResponseEntity.ok(service.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar membresía (lógico)")
	@RequirePermission("membership:gym:delete")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		service.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer membresía eliminada")
	@RequirePermission("membership:gym:reset")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		service.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar membresía (físico)")
	@RequirePermission("membership:gym:force_delete")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		service.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
