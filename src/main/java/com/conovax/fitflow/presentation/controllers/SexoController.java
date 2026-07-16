package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.SexoRequest;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.dto.response.SexoResponse;
import com.conovax.sexbody.application.services.SexoService;
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
@RequestMapping("/api/v1/sexos")
@RequiredArgsConstructor
@Tag(name = "Sexos", description = "CRUD de sexos")
@SecurityRequirement(name = "bearerAuth")
public class SexoController {

	private final SexoService sexoService;

	@GetMapping
	@Operation(summary = "Listar sexos")
	//@RequirePermission("get:all:sexo")
	public ResponseEntity<List<SexoResponse>> getAll() {
		return ResponseEntity.ok(sexoService.getAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener sexo por ID")
	@RequirePermission("get:sexo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = SexoResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<SexoResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(sexoService.getById(id));
	}

	@PostMapping
	@Operation(summary = "Crear sexo")
	@RequirePermission("create:sexo")
	public ResponseEntity<SexoResponse> create(@Valid @RequestBody SexoRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(sexoService.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar sexo")
	@RequirePermission("update:sexo")
	public ResponseEntity<SexoResponse> update(@PathVariable Long id, @Valid @RequestBody SexoRequest request) {
		return ResponseEntity.ok(sexoService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar sexo (lógico)")
	@RequirePermission("delete:sexo")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		sexoService.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer sexo eliminado")
	@RequirePermission("reset:sexo")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		sexoService.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar sexo (físico)")
	@RequirePermission("force:delete:sexo")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		sexoService.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
