package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.dto.response.UserGymMembershipDetailResponse;
import com.conovax.fitflow.application.dto.response.UserGymMembershipResponse;
import com.conovax.fitflow.application.services.UserGymMembershipService;
import com.conovax.fitflow.infrastructure.security.annotations.RequireActiveGymSubscription;
import com.conovax.fitflow.infrastructure.security.annotations.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-gym-memberships")
@RequiredArgsConstructor
@Tag(name = "UserGymMembership", description = "Asignación de membresías a usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UserGymMembershipController {

	private final UserGymMembershipService service;

	@PostMapping("/{userGymId}/{membershipId}")
	@Operation(summary = "Permite asignar membresías a un usuario de un gym")
	@RequirePermission("assign:membership:gym")
	@RequireActiveGymSubscription(enforceGymMatch = false)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Creado",
					content = @Content(schema = @Schema(implementation = UserGymMembershipResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<UserGymMembershipResponse> assignMembership(
			@PathVariable Long userGymId,
			@PathVariable Long membershipId
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(service.assignMembershipToUserGym(userGymId, membershipId));
	}

	@GetMapping("/user-gym/{userGymId}/gym/{gymId}")
	@Operation(summary = "Listar membresías asignadas de un usuario de un gym en especifico")
	@RequirePermission("list:membership:users")
	@RequireActiveGymSubscription
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok",
					content = @Content(schema = @Schema(implementation = UserGymMembershipDetailResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<List<UserGymMembershipDetailResponse>> getAllByUserGymIdAndGymId(
			@PathVariable Long userGymId,
			@PathVariable Long gymId
	) {
		return ResponseEntity.ok(service.getAllByUserGymIdAndGymId(userGymId, gymId));
	}
}
