package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.ProductRequest;
import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.dto.response.ProductInventoryResponse;
import com.conovax.fitflow.application.dto.response.ProductResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.services.ProductService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "CRUD de productos")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

	private final ProductService service;

	@GetMapping("/gym/{gymId}")
	@Operation(summary = "Listar productos por gym")
	@RequirePermission("get:all:products:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<PageResponse<ProductResponse>> getAll(
			@PathVariable Long gymId,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false) String search
	) {
		return ResponseEntity.ok(service.getAll(gymId, page, size, search));
	}

	@GetMapping("/gym/{gymId}/{productId}")
	@Operation(summary = "Obtener producto por ID")
	@RequirePermission("get:product:gym")
	@RequireActiveGymSubscription
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = ProductResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<ProductResponse> getById(@PathVariable Long gymId, @PathVariable Long productId) {
		return ResponseEntity.ok(service.getById(gymId, productId));
	}

	@GetMapping("/gym/{gymId}/search")
	@Operation(summary = "Buscar productos por coincidencia de nombre")
	@RequirePermission("get:all:product:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<List<ProductResponse>> search(
			@PathVariable Long gymId,
			@RequestParam String name
	) {
		return ResponseEntity.ok(service.search(name, gymId));
	}

	@GetMapping("/gym/{gymId}/lookup")
	@Operation(summary = "Búsqueda rápida POS: por barcode exacto o nombre/descripción (máx 10)")
	@RequirePermission("get:all:products:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<List<ProductResponse>> lookup(
			@PathVariable Long gymId,
			@RequestParam String q
	) {
		return ResponseEntity.ok(service.lookup(gymId, q));
	}

	@GetMapping("/gym/{gymId}/inventory")
	@Operation(summary = "Reporte de inventario con datos de ventas")
	@RequirePermission("get:all:products:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<List<ProductInventoryResponse>> getInventory(
			@PathVariable Long gymId,
			@RequestParam(required = false) String from,
			@RequestParam(required = false) String to
	) {
		return ResponseEntity.ok(service.getInventory(gymId, from, to));
	}

	@PostMapping("/gym/{gymId}")
	@Operation(summary = "Crear producto")
	@RequirePermission("create:product:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<ProductResponse> create(@PathVariable Long gymId, @Valid @RequestBody ProductRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(gymId, request));
	}

	@PutMapping("/gym/{gymId}/{productId}")
	@Operation(summary = "Actualizar producto")
	@RequirePermission("update:product:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<ProductResponse> update(
			@PathVariable Long gymId,
			@PathVariable Long productId,
			@Valid @RequestBody ProductRequest request
	) {
		return ResponseEntity.ok(service.update(gymId, productId, request));
	}

	@DeleteMapping("/gym/{gymId}/{productId}")
	@Operation(summary = "Eliminar producto (lógico)")
	@RequirePermission("delete:product:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<Void> deleteLogical(@PathVariable Long gymId, @PathVariable Long productId) {
		service.deleteLogical(gymId, productId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/gym/{gymId}/{productId}/reset")
	@Operation(summary = "Restablecer producto eliminado")
	@RequirePermission("reset:product:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<Void> reset(@PathVariable Long gymId, @PathVariable Long productId) {
		service.reset(gymId, productId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/gym/{gymId}/{productId}/force")
	@Operation(summary = "Eliminar producto (físico)")
	@RequirePermission("force:delete:product:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<Void> forceDelete(@PathVariable Long gymId, @PathVariable Long productId) {
		service.forceDelete(gymId, productId);
		return ResponseEntity.noContent().build();
	}
}
