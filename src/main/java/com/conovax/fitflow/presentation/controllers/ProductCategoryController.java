package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.ProductCategoryRequest;
import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.dto.response.ProductCategoryResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.services.ProductCategoryService;
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
@RequestMapping("/api/v1/product-categories")
@RequiredArgsConstructor
@Tag(name = "Product Categories", description = "CRUD de categorías de productos")
@SecurityRequirement(name = "bearerAuth")
public class ProductCategoryController {

	private final ProductCategoryService service;

	@GetMapping
	@Operation(summary = "Listar categorías")
	@RequirePermission("get:all:product:category")
	public ResponseEntity<List<ProductCategoryResponse>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/page")
	@Operation(summary = "Listar categorías paginadas con búsqueda")
	@RequirePermission("get:all:product:category")
	public ResponseEntity<PageResponse<ProductCategoryResponse>> getPage(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false, defaultValue = "") String name
	) {
		return ResponseEntity.ok(service.getPage(page, size, name));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener categoría por ID")
	@RequirePermission("get:product:category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = ProductCategoryResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<ProductCategoryResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@PostMapping
	@Operation(summary = "Crear categoría")
	@RequirePermission("create:product:category")
	public ResponseEntity<ProductCategoryResponse> create(@Valid @RequestBody ProductCategoryRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar categoría")
	@RequirePermission("update:product:category")
	public ResponseEntity<ProductCategoryResponse> update(@PathVariable Long id,
										 @Valid @RequestBody ProductCategoryRequest request) {
		return ResponseEntity.ok(service.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar categoría (lógico)")
	@RequirePermission("delete:product:category")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		service.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer categoría eliminada")
	@RequirePermission("reset:product:category")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		service.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar categoría (físico)")
	@RequirePermission("force:delete:product:category")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		service.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
