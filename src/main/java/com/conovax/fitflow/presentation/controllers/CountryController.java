package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.CountryRequest;
import com.conovax.sexbody.application.dto.response.CountryResponse;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.pagination.PageResponse;
import com.conovax.sexbody.application.services.CountryService;
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
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
@Tag(name = "Countries", description = "CRUD de países")
@SecurityRequirement(name = "bearerAuth")
public class CountryController {

	private final CountryService countryService;

	@GetMapping
	@Operation(summary = "Listar países")
	//@RequirePermission("get:all:country")
	public ResponseEntity<PageResponse<CountryResponse>> getAll(
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "15") Integer size,
			@RequestParam(required = false) String name
	) {
		return ResponseEntity.ok(countryService.getAll(page, size, name));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener país por ID")
	@RequirePermission("get:country")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = CountryResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<CountryResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(countryService.getById(id));
	}

	@GetMapping("/search")
	@Operation(summary = "Buscar países por coincidencia de nombre")
	@RequirePermission("get:all:country")
	public ResponseEntity<List<CountryResponse>> search(@RequestParam String name) {
		return ResponseEntity.ok(countryService.searchByName(name));
	}

	@PostMapping
	@Operation(summary = "Crear país")
	@RequirePermission("create:country")
	public ResponseEntity<CountryResponse> create(@Valid @RequestBody CountryRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(countryService.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar país")
	@RequirePermission("update:country")
	public ResponseEntity<CountryResponse> update(@PathVariable Long id, @Valid @RequestBody CountryRequest request) {
		return ResponseEntity.ok(countryService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar país (lógico)")
	@RequirePermission("delete:country")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		countryService.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer país eliminado")
	@RequirePermission("reset:country")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		countryService.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar país (físico)")
	@RequirePermission("force:delete:country")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		countryService.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
