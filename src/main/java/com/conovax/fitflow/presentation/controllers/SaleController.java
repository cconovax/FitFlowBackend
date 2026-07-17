package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.SaleRequest;
import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.dto.response.SaleResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.services.SaleService;
import com.conovax.fitflow.infrastructure.security.annotations.RequireActiveGymSubscription;
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
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
@Tag(name = "Sales", description = "Módulo de ventas de productos")
@SecurityRequirement(name = "bearerAuth")
public class SaleController {

	private final SaleService saleService;

	@PostMapping("/gym/{gymId}")
	@RequirePermission("create:sale:gym")
	@RequireActiveGymSubscription
	@Operation(summary = "Registrar venta", description = "Registra una venta y descuenta el stock de los productos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Venta registrada",
					content = @Content(schema = @Schema(implementation = SaleResponse.class))),
			@ApiResponse(responseCode = "400", description = "Stock insuficiente o datos inválidos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Producto no encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<SaleResponse> createSale(
			@PathVariable Long gymId,
			@Valid @RequestBody SaleRequest request
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(saleService.createSale(request));
	}

	@GetMapping("/gym/{gymId}/{saleId}")
	@RequirePermission("get:sale:gym")
	@RequireActiveGymSubscription
	@Operation(summary = "Obtener venta por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Venta encontrada",
					content = @Content(schema = @Schema(implementation = SaleResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrada",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<SaleResponse> getById(
			@PathVariable Long gymId,
			@PathVariable Long saleId
	) {
		return ResponseEntity.ok(saleService.getById(gymId, saleId));
	}

	@GetMapping("/gym/{gymId}/history")
	@RequirePermission("get:sales:history:gym")
	@RequireActiveGymSubscription
	@Operation(summary = "Historial de ventas", description = "Lista ventas del gym con filtro por fecha")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listado de ventas",
					content = @Content(schema = @Schema(implementation = SaleResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<PageResponse<SaleResponse>> getHistory(
			@PathVariable Long gymId,
			@RequestParam(required = false) String from,
			@RequestParam(required = false) String to,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "20") Integer size
	) {
		return ResponseEntity.ok(saleService.getHistory(gymId, from, to, page, size));
	}
}
