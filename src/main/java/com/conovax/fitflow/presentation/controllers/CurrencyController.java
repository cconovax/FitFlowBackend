package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.CurrencyRequest;
import com.conovax.fitflow.application.dto.response.CurrencyResponse;
import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.services.CurrencyService;
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
@RequestMapping("/api/v1/currencies")
@RequiredArgsConstructor
@Tag(name = "Currencies", description = "CRUD de monedas")
@SecurityRequirement(name = "bearerAuth")
public class CurrencyController {

	private final CurrencyService currencyService;

	@GetMapping
	@Operation(summary = "Listar monedas")
	@RequirePermission("get:all:currency")
	public ResponseEntity<PageResponse<CurrencyResponse>> getAll(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false) String search
	) {
		return ResponseEntity.ok(currencyService.getAll(page, size, search));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener moneda por ID")
	@RequirePermission("get:currency")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = CurrencyResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<CurrencyResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(currencyService.getById(id));
	}

	@PostMapping
	@Operation(summary = "Crear moneda")
	@RequirePermission("create:currency")
	public ResponseEntity<CurrencyResponse> create(@Valid @RequestBody CurrencyRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(currencyService.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar moneda")
	@RequirePermission("update:currency")
	public ResponseEntity<CurrencyResponse> update(@PathVariable Long id, @Valid @RequestBody CurrencyRequest request) {
		return ResponseEntity.ok(currencyService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar moneda (lógico)")
	@RequirePermission("delete:currency")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		currencyService.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer moneda eliminada")
	@RequirePermission("reset:currency")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		currencyService.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar moneda (físico)")
	@RequirePermission("force:delete:currency")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		currencyService.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}