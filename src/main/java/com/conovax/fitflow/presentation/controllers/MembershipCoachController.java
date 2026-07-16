package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.MembershipCoachRequest;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.dto.response.MembershipActiveUserResponse;
import com.conovax.sexbody.application.dto.response.MembershipCoachResponse;
import com.conovax.sexbody.application.services.MembershipCoachService;
import com.conovax.sexbody.infrastructure.security.annotations.RequireActiveGymSubscription;
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
@RequestMapping("/api/v1/membership-coachs")
@RequiredArgsConstructor
@Tag(name = "Membership Coaches", description = "Asignación de entrenadores a membresías")
@SecurityRequirement(name = "bearerAuth")
public class MembershipCoachController {

	private final MembershipCoachService service;

	@GetMapping("/gym/{gymId}")
	@Operation(summary = "Listar asignaciones por gym")
	@RequirePermission("get:membership:coach")
	@RequireActiveGymSubscription
	public ResponseEntity<List<MembershipCoachResponse>> getAllByGym(@PathVariable Long gymId) {
		return ResponseEntity.ok(service.getAllByGymId(gymId));
	}

	@GetMapping("/coach/{coachId}")
	@Operation(summary = "Listar asignaciones por entrenador")
	@RequirePermission("get:membership:coach")
	public ResponseEntity<List<MembershipCoachResponse>> getAllByCoach(@PathVariable Long coachId) {
		return ResponseEntity.ok(service.getAllByCoachId(coachId));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener asignación por ID")
	@RequirePermission("get:membership:coach")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = MembershipCoachResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<MembershipCoachResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@PostMapping
	@Operation(summary = "Asignar entrenador a membresía")
	@RequirePermission("assign:membership:coach")
	public ResponseEntity<MembershipCoachResponse> create(@Valid @RequestBody MembershipCoachRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar asignación (lógico)")
	@RequirePermission("delete:membership:coach")
	public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
		service.deleteLogical(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/reset")
	@Operation(summary = "Restablecer asignación")
	@RequirePermission("reset:membership:coach")
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		service.reset(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/force")
	@Operation(summary = "Eliminar asignación (físico)")
	@RequirePermission("force:delete:membership:coach")
	public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
		service.forceDelete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{membershipId}/active-users")
	@Operation(summary = "Listar usuarios con membresía activa")
	@RequirePermission("get:active:user:membership:rating")
	public ResponseEntity<List<MembershipActiveUserResponse>> getActiveUsers(
			@PathVariable Long membershipId,
			@RequestParam Long coachId
	) {
		return ResponseEntity.ok(service.getActiveUsersByMembership(membershipId, coachId));
	}
}
