package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.PeopleRequest;
import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.dto.response.PeopleNumDocumentExistsResponse;
import com.conovax.fitflow.application.dto.response.PeopleResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.services.PeopleService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/peoples")
@RequiredArgsConstructor
@Tag(name = "Peoples", description = "Endpoints para consultar personas")
@SecurityRequirement(name = "bearerAuth")
public class PeopleController {

	private final PeopleService peopleService;

	@GetMapping
	@RequirePermission("create:user:gym")
	@Operation(summary = "Listar People", description = "Lista paginada de personas activas (status=true)")
	public ResponseEntity<PageResponse<PeopleResponse>> getAll(
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "15") Integer size
	) {
		return ResponseEntity.ok(peopleService.getAll(page, size));
	}

	@GetMapping("/exists/num-document")
	@RequirePermission("create:user:gym")
	@Operation(
			summary = "Verificar si un número de documento ya está registrado",
			description = "Retorna exists=true si existe un People activo (status=true) con ese numDocument"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Resultado de la verificación",
					content = @Content(schema = @Schema(implementation = PeopleNumDocumentExistsResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos suficientes",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<PeopleNumDocumentExistsResponse> existsNumDocument(@RequestParam String numDocument) {
		boolean exists = peopleService.isNumDocumentRegistered(numDocument);
		return ResponseEntity.ok(new PeopleNumDocumentExistsResponse(exists));
	}

	@GetMapping("/{peopleId}")
	@RequirePermission("create:user:gym")
	@Operation(summary = "Obtener People por ID", description = "Obtiene una persona por peopleId (sin contraseña)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "People encontrado",
					content = @Content(schema = @Schema(implementation = PeopleResponse.class))),
			@ApiResponse(responseCode = "404", description = "People no encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos suficientes",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<PeopleResponse> getById(@PathVariable Long peopleId) {
		return ResponseEntity.ok(peopleService.getById(peopleId));
	}

	@PostMapping
	@RequirePermission("create:user:gym")
	@Operation(summary = "Crear People", description = "Crea una persona (status=true)")
	public ResponseEntity<PeopleResponse> create(@Valid @RequestBody PeopleRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(peopleService.create(request));
	}

	@PutMapping("/{peopleId}")
	@RequirePermission("edit:user:gym")
	@Operation(summary = "Actualizar People", description = "Actualiza una persona activa")
	public ResponseEntity<PeopleResponse> update(@PathVariable Long peopleId, @Valid @RequestBody PeopleRequest request) {
		return ResponseEntity.ok(peopleService.update(peopleId, request));
	}

	@DeleteMapping("/{peopleId}")
	@RequirePermission("delete:user:gym")
	@Operation(summary = "Eliminar People (lógico)", description = "Eliminado lógico: status=false")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long peopleId) {
		peopleService.deleteLogical(peopleId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{peopleId}/reset")
	@RequirePermission("reset:user:gym")
	@Operation(summary = "Restablecer People eliminado", description = "Revierte eliminado lógico: status=true")
	public ResponseEntity<Void> reset(@PathVariable Long peopleId) {
		peopleService.reset(peopleId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{peopleId}/force")
	@RequirePermission("force:delete:user:gym")
	@Operation(summary = "Eliminar People (físico)", description = "Elimina definitivamente el registro")
	public ResponseEntity<Void> forceDelete(@PathVariable Long peopleId) {
		peopleService.forceDelete(peopleId);
		return ResponseEntity.noContent().build();
	}
}
