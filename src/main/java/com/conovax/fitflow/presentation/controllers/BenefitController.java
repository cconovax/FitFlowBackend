package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.BenefitRequest;
import com.conovax.sexbody.application.dto.response.BenefitResponse;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.pagination.PageResponse;
import com.conovax.sexbody.application.services.BenefitService;
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
@RequestMapping("/api/v1/benefits")
@RequiredArgsConstructor
@Tag(name = "Benefits", description = "CRUD de beneficios")
@SecurityRequirement(name = "bearerAuth")
public class BenefitController {

	private final BenefitService service;

	@GetMapping
	@Operation(summary = "Listar beneficios")
	@RequirePermission("get:all:benefit:gym")
	public ResponseEntity<List<BenefitResponse>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/gym/{gymId}")
	@Operation(summary = "Listar beneficios por gym (incluye globales)")
	@RequirePermission("get:all:benefit:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<PageResponse<BenefitResponse>> getAllByGymOrGlobal(
			@PathVariable Long gymId,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false) String search
	) {
		return ResponseEntity.ok(service.getAllByGymOrGlobal(gymId, page, size, search));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener beneficio por ID")
	@RequirePermission("get:benefit:gym")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = BenefitResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<BenefitResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@PostMapping
	@Operation(summary = "Crear beneficio")
	@RequirePermission("create:benefit:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<BenefitResponse> create(@Valid @RequestBody BenefitRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar beneficio")
	@RequirePermission("update:benefit:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<BenefitResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody BenefitRequest request
	) {
		return ResponseEntity.ok(service.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar beneficio (lógico)")
	@RequirePermission("delete:benefit:gym")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		service.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer beneficio eliminado")
	@RequirePermission("reset:benefit:gym")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		service.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar beneficio (físico)")
	@RequirePermission("force:delete:benefit:gym")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		service.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
