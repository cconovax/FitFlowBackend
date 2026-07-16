package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.response.GymDashboardResponse;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.services.GymDashboardService;
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
@RequestMapping("/api/v1/gym-dashboard")
@RequiredArgsConstructor
@Tag(name = "GymDashboard", description = "Dashboard de estadísticas del gym")
@SecurityRequirement(name = "bearerAuth")
public class GymDashboardController {

	private final GymDashboardService service;

	@GetMapping("/{gymId}")
	@Operation(summary = "Obtiene el dashboard completo del gym: KPIs, sesiones diarias, ventas y distribución de membresías")
	@RequirePermission("gym:dashboard:view")
	@RequireActiveGymSubscription
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok",
					content = @Content(schema = @Schema(implementation = GymDashboardResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<GymDashboardResponse> getDashboard(@PathVariable Long gymId) {
		return ResponseEntity.ok(service.getDashboard(gymId));
	}
}
