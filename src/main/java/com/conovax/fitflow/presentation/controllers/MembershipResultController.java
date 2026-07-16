package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.MembershipResultRequest;
import com.conovax.sexbody.application.dto.response.MembershipResultResponse;
import com.conovax.sexbody.application.services.MembershipResultService;
import com.conovax.sexbody.infrastructure.security.annotations.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/membership-results")
@RequiredArgsConstructor
@Tag(name = "Membership Results", description = "Resultados de membresías")
@SecurityRequirement(name = "bearerAuth")
public class MembershipResultController {

	private final MembershipResultService service;

	@GetMapping("/user-gym-membership/{userGymMembershipId}")
	@Operation(summary = "Obtener resultado por membresía asignada")
	@RequirePermission("get:result:membership:user:gym")
	public ResponseEntity<MembershipResultResponse> getByUserGymMembership(@PathVariable Long userGymMembershipId) {
		return ResponseEntity.ok(service.getByUserGymMembershipId(userGymMembershipId));
	}

	@PostMapping("/{userGymMembershipId}/calculate")
	@Operation(summary = "Calcular resultado automáticamente desde evaluaciones")
	@RequirePermission("calculate:membership:result")
	public ResponseEntity<MembershipResultResponse> calculate(
			@PathVariable Long userGymMembershipId,
			@RequestParam Long coachId) {
		return ResponseEntity.ok(service.calculateFromRatings(userGymMembershipId, coachId));
	}

	@PostMapping
	@Operation(summary = "Crear o actualizar resultado")
	@RequirePermission("create:membership:result")
	public ResponseEntity<MembershipResultResponse> createOrUpdate(
			@Valid @RequestBody MembershipResultRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrUpdate(request));
	}
}
