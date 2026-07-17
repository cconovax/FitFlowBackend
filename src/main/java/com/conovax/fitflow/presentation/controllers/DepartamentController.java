package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.DepartamentRequest;
import com.conovax.fitflow.application.dto.response.DepartamentResponse;
import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.services.DepartamentService;
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

@RestController
@RequestMapping("/api/v1/departaments")
@RequiredArgsConstructor
@Tag(name = "Departaments", description = "CRUD de departamentos")
@SecurityRequirement(name = "bearerAuth")
public class DepartamentController {

	private final DepartamentService departamentService;

	@GetMapping
	@Operation(summary = "Listar departamentos")
	@RequirePermission("get:all:departments")
	public ResponseEntity<PageResponse<DepartamentResponse>> getAll(
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "15") Integer size,
			@RequestParam(required = false) String name
	) {
		return ResponseEntity.ok(departamentService.getAll(page, size, name));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener departamento por ID")
	@RequirePermission("get:department")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = DepartamentResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<DepartamentResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(departamentService.getById(id));
	}

	@GetMapping("/country/{countryId}")
	@Operation(summary = "Listar departamentos por país")
	//@RequirePermission("get:departments:for:country")
	public ResponseEntity<PageResponse<DepartamentResponse>> getAllByCountry(
			@PathVariable Long countryId,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "15") Integer size
	) {
		return ResponseEntity.ok(departamentService.getAllByCountry(countryId, page, size));
	}

	@PostMapping
	@Operation(summary = "Crear departamento")
	@RequirePermission("create:department")
	public ResponseEntity<DepartamentResponse> create(@Valid @RequestBody DepartamentRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(departamentService.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar departamento")
	@RequirePermission("update:department")
	public ResponseEntity<DepartamentResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody DepartamentRequest request
	) {
		return ResponseEntity.ok(departamentService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar departamento (lógico)")
	@RequirePermission("delete:department")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		departamentService.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer departamento eliminado")
	@RequirePermission("update:department")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		departamentService.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar departamento (físico)")
	@RequirePermission("force:delete:department")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		departamentService.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
