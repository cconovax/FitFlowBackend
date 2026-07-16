package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.MunicipalityRequest;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.dto.response.MunicipalityResponse;
import com.conovax.sexbody.application.pagination.PageResponse;
import com.conovax.sexbody.application.services.MunicipalityService;
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
@RequestMapping("/api/v1/municipalities")
@RequiredArgsConstructor
@Tag(name = "Municipalities", description = "CRUD de municipios")
@SecurityRequirement(name = "bearerAuth")
public class MunicipalityController {

	private final MunicipalityService municipalityService;

	@GetMapping
	@Operation(summary = "Listar municipios")
	@RequirePermission("get:all:municipality")
	public ResponseEntity<PageResponse<MunicipalityResponse>> getAll(
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "15") Integer size,
			@RequestParam(required = false) String name
	) {
		return ResponseEntity.ok(municipalityService.getAll(page, size, name));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener municipio por ID")
	@RequirePermission("get:municipality")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = MunicipalityResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<MunicipalityResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(municipalityService.getById(id));
	}

	@GetMapping("/departament/{departamentId}")
	@Operation(summary = "Listar municipios por departamento")
	//@RequirePermission("get:municipality")
	public ResponseEntity<java.util.List<MunicipalityResponse>> getAllByDepartament(@PathVariable Long departamentId) {
		return ResponseEntity.ok(municipalityService.getAllByDepartament(departamentId));
	}

	@PostMapping
	@Operation(summary = "Crear municipio")
	@RequirePermission("create:municipality")
	public ResponseEntity<MunicipalityResponse> create(@Valid @RequestBody MunicipalityRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(municipalityService.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar municipio")
	@RequirePermission("update:municipality")
	public ResponseEntity<MunicipalityResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody MunicipalityRequest request
	) {
		return ResponseEntity.ok(municipalityService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar municipio (lógico)")
	@RequirePermission("delete:municipality")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		municipalityService.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer municipio eliminado")
	@RequirePermission("reset:municipality")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		municipalityService.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar municipio (físico)")
	@RequirePermission("force:delete:municipality")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		municipalityService.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
