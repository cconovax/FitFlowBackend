package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.GymUserRegisterRequest;
import com.conovax.fitflow.application.dto.request.GymUserRelationRequest;
import com.conovax.fitflow.application.dto.response.GymUserRelationLookupResponse;
import com.conovax.fitflow.application.dto.response.UserResponse;
import com.conovax.fitflow.application.services.GymUserRegisterService;
import com.conovax.fitflow.infrastructure.security.annotations.RequireActiveGymSubscription;
import com.conovax.fitflow.infrastructure.security.annotations.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gyms/{gymId}/users")
@RequiredArgsConstructor
@Tag(name = "Gym Users", description = "Registro de usuarios referenciados a un gym")
@SecurityRequirement(name = "bearerAuth")
public class GymUserRegisterController {

	private final GymUserRegisterService gymUserRegisterService;

	@PostMapping("/register")
	@Operation(summary = "Registrar usuario en un gym", description = "Crea el usuario en auth (sin contraseña; usa numDocument) y lo referencia al gym vía users_gyms")
	@RequirePermission("create:user:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<UserResponse> registerUserInGym(
			@PathVariable Long gymId,
			@Valid @RequestBody GymUserRegisterRequest request
	) {
		UserResponse created = gymUserRegisterService.registerUserInGym(gymId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PostMapping("/relation")
	@Operation(summary = "Relacionar usuario existente a uno o varios gyms", description = "Relaciona un usuario existente (por numDocument) al gym de la ruta y opcionalmente a más gymIds enviados en el body; guarda fingerprint en users_gyms y asigna uno o varios roles en user_gym_role")
	@RequirePermission("create:user:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<Void> relationUserInGym(
			@PathVariable Long gymId,
			@Valid @RequestBody GymUserRelationRequest request
	) {
		gymUserRegisterService.relationUserInGym(gymId, request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/relation/lookup")
	@Operation(summary = "Consultar gyms y roles de un usuario existente", description = "Busca un usuario por numDocument y devuelve los gyms a los que está relacionado y los roles que tiene asignados, para precargar el modal de relacionar")
	@RequirePermission("create:user:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<GymUserRelationLookupResponse> lookupUserRelation(
			@PathVariable Long gymId,
			@RequestParam String numDocument
	) {
		return ResponseEntity.ok(gymUserRegisterService.lookupUserRelation(numDocument));
	}

	@DeleteMapping("/{userGymId}")
	@Operation(summary = "Eliminar relación user_gym (lógico)", description = "Marca la relación users_gyms.status=false")
	@RequirePermission("delete:user:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<Void> deleteLogicalUserGym(
			@PathVariable Long gymId,
			@PathVariable Long userGymId
	) {
		gymUserRegisterService.deleteLogicalUserGym(gymId, userGymId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{userGymId}/reset")
	@Operation(summary = "Restablecer relación user_gym", description = "Revierte eliminado lógico: users_gyms.status=true")
	@RequirePermission("reset:user:gym")
	@RequireActiveGymSubscription
	public ResponseEntity<Void> resetUserGym(
			@PathVariable Long gymId,
			@PathVariable Long userGymId
	) {
		gymUserRegisterService.resetUserGym(gymId, userGymId);
		return ResponseEntity.noContent().build();
	}
}
