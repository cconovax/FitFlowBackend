package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.response.ClientDashboardResponse;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.services.ClientDashboardService;
import com.conovax.sexbody.infrastructure.security.annotations.RequireActiveGymSubscription;
import com.conovax.sexbody.infrastructure.security.annotations.RequirePermission;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client-dashboard")
@RequiredArgsConstructor
@Tag(name = "ClientDashboard", description = "Dashboard personal del cliente del gym")
@SecurityRequirement(name = "bearerAuth")
public class ClientDashboardController {

	private final ClientDashboardService service;

	@GetMapping("/gym/{gymId}/user/{userId}")
	@Operation(summary = "Obtiene el dashboard completo del cliente: membresía activa, asistencia, métricas corporales y progreso")
	@RequirePermission("client:dashboard:view")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok",
					content = @Content(schema = @Schema(implementation = ClientDashboardResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<ClientDashboardResponse> getDashboard(
			@PathVariable Long gymId,
			@PathVariable Long userId
	) {
		return ResponseEntity.ok(service.getDashboard(gymId, userId));
	}
}
