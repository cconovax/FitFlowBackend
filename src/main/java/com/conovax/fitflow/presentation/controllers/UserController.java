package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.UserGymsByEmailRequest;
import com.conovax.fitflow.application.dto.request.UserRegisterWithGymsRequest;
import com.conovax.fitflow.application.dto.request.GymUserRelationRequest;
import com.conovax.fitflow.application.dto.request.ChangePasswordRequest;
import com.conovax.fitflow.application.dto.request.UpdateProfileRequest;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.dto.response.ErrorResponse;
import com.conovax.fitflow.application.dto.response.LoginGymsResponse;
import com.conovax.fitflow.application.dto.response.PeopleResponse;
import com.conovax.fitflow.application.dto.response.UserGymUserResponse;
import com.conovax.fitflow.application.dto.response.UserResponse;
import com.conovax.fitflow.application.services.UserGymUsersService;
import com.conovax.fitflow.application.services.GymUserRegisterService;
import com.conovax.fitflow.application.services.UserRegisterWithGymsService;
import com.conovax.fitflow.application.services.UserService;
import com.conovax.fitflow.infrastructure.security.annotations.RequireActiveGymSubscription;
import com.conovax.fitflow.infrastructure.security.annotations.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Endpoints para gestión de usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

	private final UserService userService;
	private final UserRegisterWithGymsService userRegisterWithGymsService;
	private final UserGymUsersService userGymUsersService;
	private final GymUserRegisterService gymUserRegisterService;

	@PostMapping("/register-with-gyms")
	@RequirePermission("create:user:gym")
	@Operation(
			summary = "Registrar usuario y relacionarlo a múltiples gyms",
			description = "Crea People+User (incluye contraseña opcional) y guarda relaciones en users_gyms para cada gymId; además asigna los roles enviados en roleIds"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario creado y relacionado exitosamente",
					content = @Content(schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos suficientes",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<UserResponse> registerWithGyms(@Valid @RequestBody UserRegisterWithGymsRequest request) {
		UserResponse created = userRegisterWithGymsService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PostMapping("/relation-gyms")
	@RequirePermission("relation:user:gym")
	@Operation(
			summary = "Relacionar usuario existente a múltiples gyms",
			description = "Busca un usuario por numDocument y lo relaciona a múltiples gyms enviados en gymIds, asignando los roles enviados en roleIds para cada relación user_gym"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario relacionado exitosamente a los gyms solicitados"),
			@ApiResponse(responseCode = "403", description = "Sin permisos suficientes",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<Void> relationUserWithGyms(@Valid @RequestBody GymUserRelationRequest request) {
		gymUserRegisterService.relationUserInGyms(request.gymIds(), request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/gym/{gymId}")
	@RequirePermission("get:all:users:gym")
	@RequireActiveGymSubscription
	@Operation(
			summary = "Listar usuarios por gym",
			description = "Carga los datos relacionados de users_gyms + users + peoples para un gym específico (sin contraseña). Opcionalmente permite filtrar por coincidencia de nombres/apellidos o número de documento usando el parámetro 'search'"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de usuarios por gym",
					content = @Content(schema = @Schema(implementation = UserGymUserResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos suficientes",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<PageResponse<UserGymUserResponse>> getUsersByGym(
			@PathVariable Long gymId,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false) String search
	) {
		return ResponseEntity.ok(userGymUsersService.getUsersByGymId(gymId, page, size, search));
	}

	@GetMapping("/gym/{gymId}/trainers")
	@RequirePermission("list:all:coach:gym")
	@RequireActiveGymSubscription
	@Operation(
			summary = "Listar entrenadores por gym",
			description = "Devuelve usuarios del gym que tengan el rol Entrenador. Permite filtrar por nombre o documento usando search."
	)
	public ResponseEntity<List<UserGymUserResponse>> getTrainersByGym(
			@PathVariable Long gymId,
			@RequestParam(required = false) String search
	) {
		return ResponseEntity.ok(userGymUsersService.getTrainersByGymId(gymId, search));
	}

	@PostMapping("/gyms")
	@Operation(
			summary = "Obtener gyms por email",
			description = "Busca el usuario por email y devuelve los gyms asociados"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de gyms por email",
					content = @Content(schema = @Schema(implementation = LoginGymsResponse.class))),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<LoginGymsResponse> getGymsByEmail(@Valid @RequestBody UserGymsByEmailRequest request) {
		return ResponseEntity.ok(userService.getGymsByEmail(request.email()));
	}

	@GetMapping
	@RequirePermission("get:all:users")
	@Operation(summary = "Listar todos los usuarios", description = "Obtiene la lista de todos los usuarios del sistema. Requiere permiso 'user:read'")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
					content = @Content(schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos suficientes",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		List<UserResponse> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	@RequirePermission("get:user")
	@Operation(summary = "Obtener usuario por ID", description = "Obtiene la información de un usuario específico. Requiere permiso 'user:read'")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario encontrado",
					content = @Content(schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos suficientes",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		UserResponse user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/{id}")
	@RequirePermission("delete:user")
	@Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema. Requiere permiso 'delete:user'")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Sin permisos suficientes",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	// ── Profile (authenticated user) ────────────────────────────────

	@GetMapping("/me")
	@Operation(summary = "Obtener mi perfil", description = "Retorna los datos personales del usuario autenticado")
	public ResponseEntity<PeopleResponse> getMyProfile() {
		return ResponseEntity.ok(userService.getMyProfile());
	}

	@PutMapping("/me")
	@Operation(summary = "Actualizar mi perfil", description = "Actualiza los datos personales del usuario autenticado")
	public ResponseEntity<PeopleResponse> updateMyProfile(@Valid @RequestBody UpdateProfileRequest request) {
		return ResponseEntity.ok(userService.updateMyProfile(request));
	}

	@PutMapping("/me/password")
	@Operation(summary = "Cambiar contraseña", description = "Cambia la contraseña del usuario autenticado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Contraseña cambiada exitosamente"),
			@ApiResponse(responseCode = "400", description = "Contraseña actual incorrecta",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<Void> changeMyPassword(@Valid @RequestBody ChangePasswordRequest request) {
		userService.changeMyPassword(request);
		return ResponseEntity.noContent().build();
	}
}
