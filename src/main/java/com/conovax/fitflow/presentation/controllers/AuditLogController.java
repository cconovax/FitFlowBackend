package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.response.AuditLogResponse;
import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.services.AuditLogService;
import com.conovax.fitflow.infrastructure.security.annotations.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gyms/{gymId}/audit-logs")
@RequiredArgsConstructor
@Tag(name = "Audit Logs", description = "Registros de auditoría del gym (quién, qué, desde qué IP)")
@SecurityRequirement(name = "bearerAuth")
public class AuditLogController {

	private final AuditLogService service;

	@GetMapping
	@Operation(summary = "Listar registros de auditoría del gym con filtros")
	@RequirePermission("gym:audit:view")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listado paginado de registros",
					content = @Content(schema = @Schema(implementation = AuditLogResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<PageResponse<AuditLogResponse>> search(
			@PathVariable Long gymId,
			@RequestParam(required = false) Long userId,
			@RequestParam(required = false) String action,
			@RequestParam(required = false) String from,
			@RequestParam(required = false) String to,
			@RequestParam(required = false) String q,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "20") Integer size
	) {
		return ResponseEntity.ok(service.search(gymId, userId, action, from, to, q, page, size));
	}
}
