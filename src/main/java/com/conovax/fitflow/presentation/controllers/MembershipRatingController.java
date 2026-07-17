package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.MembershipRatingRequest;
import com.conovax.fitflow.application.dto.response.MembershipRatingResponse;
import com.conovax.fitflow.application.services.MembershipRatingService;
import com.conovax.fitflow.infrastructure.security.annotations.RequirePermission;
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
@RequestMapping("/api/v1/membership-ratings")
@RequiredArgsConstructor
@Tag(name = "Membership Ratings", description = "Evaluaciones por membresía")
@SecurityRequirement(name = "bearerAuth")
public class MembershipRatingController {

	private final MembershipRatingService service;

	@GetMapping("/user-gym-membership/{userGymMembershipId}")
	@Operation(summary = "Permite listar las evaluaciones por membresía asignada de los usuarios")
	@RequirePermission("view:results:membership:rating")
	public ResponseEntity<List<MembershipRatingResponse>> getAllByUserGymMembership(@PathVariable Long userGymMembershipId) {
		return ResponseEntity.ok(service.getAllByUserGymMembershipId(userGymMembershipId));
	}

	@PostMapping
	@Operation(summary = "Crear evaluación")
	@RequirePermission("create:membership:rating")
	public ResponseEntity<MembershipRatingResponse> create(@Valid @RequestBody MembershipRatingRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar evaluación (físico)")
	@RequirePermission("delete:membership:rating")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		service.forceDelete(id);
		return ResponseEntity.noContent().build();
	}
}
