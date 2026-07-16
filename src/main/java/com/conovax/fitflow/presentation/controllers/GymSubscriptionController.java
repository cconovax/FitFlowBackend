package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.GymSubscriptionRequest;
import com.conovax.sexbody.application.dto.response.GymSubscriptionResponse;
import com.conovax.sexbody.application.services.GymSubscriptionService;
import com.conovax.sexbody.infrastructure.security.annotations.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gyms/{gymId}/subscription")
@RequiredArgsConstructor
@Tag(name = "Gym Subscription", description = "Gestión de suscripciones comerciales por gym")
@SecurityRequirement(name = "bearerAuth")
public class GymSubscriptionController {

	private final GymSubscriptionService gymSubscriptionService;

	@GetMapping
	@Operation(summary = "Obtener el estado actual de suscripción del gym")
	@RequirePermission("get:status:subscription:gym")
	public ResponseEntity<GymSubscriptionResponse> getCurrent(@PathVariable Long gymId) {
		return ResponseEntity.ok(gymSubscriptionService.getCurrentByGymId(gymId));
	}

	@GetMapping("/history")
	@Operation(summary = "Obtener el historial de suscripciones del gym")
	@RequirePermission("get:history:subscription:gym")
	public ResponseEntity<List<GymSubscriptionResponse>> getHistory(@PathVariable Long gymId) {
		return ResponseEntity.ok(gymSubscriptionService.getHistoryByGymId(gymId));
	}

	@PostMapping
	@Operation(summary = "Registrar una nueva suscripción para el gym")
	@RequirePermission("create:subscription:gym")
	public ResponseEntity<GymSubscriptionResponse> create(
			@PathVariable Long gymId,
			@Valid @RequestBody GymSubscriptionRequest request
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(gymSubscriptionService.create(gymId, request));
	}

	@PutMapping("/{subscriptionId}")
	@Operation(summary = "Actualizar un registro de suscripción del gym")
	@RequirePermission("update:subscription:gym")
	public ResponseEntity<GymSubscriptionResponse> update(
			@PathVariable Long gymId,
			@PathVariable Long subscriptionId,
			@Valid @RequestBody GymSubscriptionRequest request
	) {
		return ResponseEntity.ok(gymSubscriptionService.update(gymId, subscriptionId, request));
	}
}