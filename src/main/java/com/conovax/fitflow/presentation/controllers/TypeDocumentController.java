package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.TypeDocumentRequest;
import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.dto.response.TypeDocumentResponse;
import com.conovax.fitflow.application.services.TypeDocumentService;
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
@RequestMapping("/api/v1/type-documents")
@RequiredArgsConstructor
@Tag(name = "Type Documents", description = "CRUD de tipos de documento")
@SecurityRequirement(name = "bearerAuth")
public class TypeDocumentController {

	private final TypeDocumentService typeDocumentService;

	@GetMapping
	@Operation(summary = "Listar tipos de documento")
	//@RequirePermission("list:all:type:documents")
	public ResponseEntity<List<TypeDocumentResponse>> getAll() {
		return ResponseEntity.ok(typeDocumentService.getAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener tipo de documento por ID")
	@RequirePermission("get:type:document")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = TypeDocumentResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<TypeDocumentResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(typeDocumentService.getById(id));
	}

	@PostMapping
	@Operation(summary = "Crear tipo de documento")
	@RequirePermission("create:type:document")
	public ResponseEntity<TypeDocumentResponse> create(@Valid @RequestBody TypeDocumentRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(typeDocumentService.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar tipo de documento")
	@RequirePermission("update:type:document")
	public ResponseEntity<TypeDocumentResponse> update(@PathVariable Long id, @Valid @RequestBody TypeDocumentRequest request) {
		return ResponseEntity.ok(typeDocumentService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar tipo de documento (lógico)")
	@RequirePermission("delete:type:document")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		typeDocumentService.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer tipo de documento eliminado")
	@RequirePermission("reset:type:document")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		typeDocumentService.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar tipo de documento (físico)")
	@RequirePermission("force:delete:type:document")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		typeDocumentService.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
