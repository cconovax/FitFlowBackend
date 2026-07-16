package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.AccessDeviceRequest;
import com.conovax.sexbody.application.dto.response.AccessDeviceResponse;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import com.conovax.sexbody.application.services.AccessDeviceService;
import com.conovax.sexbody.infrastructure.security.annotations.RequireActiveGymSubscription;
import com.conovax.sexbody.infrastructure.security.annotations.RequireFeature;
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
@RequestMapping("/api/v1/gyms/{gymId}/devices")
@RequiredArgsConstructor
@Tag(name = "Access Devices", description = "Gestión de dispositivos de acceso del gym")
@SecurityRequirement(name = "bearerAuth")
public class AccessDeviceController {

	private final AccessDeviceService service;

	@GetMapping
	@Operation(summary = "Listar dispositivos del gym")
	@RequirePermission("gym:devices:view")
	@RequireFeature("BIOMETRIC_ACCESS")
	@RequireActiveGymSubscription
	public ResponseEntity<List<AccessDeviceResponse>> getAll(@PathVariable Long gymId) {
		return ResponseEntity.ok(service.getAllByGymId(gymId));
	}

	@GetMapping("/{deviceId}")
	@Operation(summary = "Obtener dispositivo por ID")
	@RequirePermission("gym:devices:view")
	@RequireFeature("BIOMETRIC_ACCESS")
	@RequireActiveGymSubscription
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrado",
					content = @Content(schema = @Schema(implementation = AccessDeviceResponse.class))),
			@ApiResponse(responseCode = "404", description = "No encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<AccessDeviceResponse> getById(
			@PathVariable Long gymId,
			@PathVariable Long deviceId
	) {
		return ResponseEntity.ok(service.getById(gymId, deviceId));
	}

	@PostMapping
	@Operation(summary = "Registrar nuevo dispositivo")
	@RequirePermission("gym:devices:create")
	@RequireFeature("BIOMETRIC_ACCESS")
	@RequireActiveGymSubscription
	public ResponseEntity<AccessDeviceResponse> create(
			@PathVariable Long gymId,
			@Valid @RequestBody AccessDeviceRequest request
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(gymId, request));
	}

	@PutMapping("/{deviceId}")
	@Operation(summary = "Actualizar dispositivo")
	@RequirePermission("gym:devices:update")
	@RequireFeature("BIOMETRIC_ACCESS")
	@RequireActiveGymSubscription
	public ResponseEntity<AccessDeviceResponse> update(
			@PathVariable Long gymId,
			@PathVariable Long deviceId,
			@Valid @RequestBody AccessDeviceRequest request
	) {
		return ResponseEntity.ok(service.update(gymId, deviceId, request));
	}

	@DeleteMapping("/{deviceId}")
	@Operation(summary = "Eliminar dispositivo")
	@RequirePermission("gym:devices:delete")
	@RequireFeature("BIOMETRIC_ACCESS")
	@RequireActiveGymSubscription
	public ResponseEntity<Void> delete(
			@PathVariable Long gymId,
			@PathVariable Long deviceId
	) {
		service.delete(gymId, deviceId);
		return ResponseEntity.noContent().build();
	}
}
