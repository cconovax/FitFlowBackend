package com.conovax.fitflow.shared.application.services;

import com.conovax.fitflow.subscription.application.services.GymSubscriptionService;
import com.conovax.fitflow.shared.application.dto.request.LoginWithGymRequest;
import com.conovax.fitflow.shared.application.dto.request.RegisterRequest;
import com.conovax.fitflow.shared.application.dto.response.AuthGymResponse;
import com.conovax.fitflow.shared.application.dto.response.CurrentUserResponse;
import com.conovax.fitflow.gym.application.dto.response.GymInfoResponse;
import com.conovax.fitflow.subscription.application.dto.response.GymSubscriptionResponse;
import com.conovax.fitflow.shared.application.dto.response.UserGymPermissionsResponse;
import com.conovax.fitflow.gym.application.dto.response.UserResponse;
import com.conovax.fitflow.notifications.application.services.NotificationService;
import com.conovax.fitflow.gym.domain.entities.GymInfo;
import com.conovax.fitflow.shared.domain.entities.People;
import com.conovax.fitflow.shared.domain.entities.User;
import com.conovax.fitflow.shared.domain.exceptions.DuplicateResourceException;
import com.conovax.fitflow.shared.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.shared.domain.repositories.MunicipalityRepository;
import com.conovax.fitflow.shared.domain.repositories.SexoRepository;
import com.conovax.fitflow.shared.domain.repositories.TypeDocumentRepository;
import com.conovax.fitflow.shared.domain.repositories.UserRepository;
import com.conovax.fitflow.gym.domain.repositories.UsersGymRepository;
import com.conovax.fitflow.shared.infrastructure.security.GymAuthenticationDetails;
import com.conovax.fitflow.shared.infrastructure.security.UserDetailsImpl;
import com.conovax.fitflow.shared.infrastructure.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${cloudinary.default-profile-photo-url}")
	private String DEFAULT_PROFILE_PHOTO_URL;

    @Value("${app.reset-password.base-url}")
    private String URL_FRONTEND_LOGIN;

	private final UserRepository userRepository;
	private final NotificationService notificationService;
	private final MunicipalityRepository municipalityRepository;
	private final SexoRepository sexoRepository;
	private final TypeDocumentRepository typeDocumentRepository;
	private final UsersGymRepository usersGymRepository;
	private final RoleService roleService;
	private final GymSubscriptionService gymSubscriptionService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	/** Expuesto para uso interno (ej: OnboardingService). */
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * Re-emite un JWT para la sesión autenticada actual con expiración renovada y los mismos
	 * claims (gymId, userGymId y permisos). Requiere una autenticación válida en el contexto
	 * (el {@code JwtAuthenticationFilter} la coloca a partir de la cookie/token vigente).
	 */
	public String refreshCurrentToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()
				|| !(authentication.getPrincipal() instanceof UserDetailsImpl)) {
			throw new ResourceNotFoundException("No hay una sesión válida para renovar");
		}

		Long gymId = null;
		Long userGymId = null;
		if (authentication.getDetails() instanceof GymAuthenticationDetails gymDetails) {
			gymId = gymDetails.gymId();
			userGymId = gymDetails.userGymId();
		}

		return jwtUtils.generateJwtToken(authentication, gymId, userGymId);
	}

	@Transactional
	public UserResponse register(RegisterRequest request) {
		if (userRepository.existsByNumDocument(request.numDocument())) {
			throw new DuplicateResourceException("El número de documento ya está registrado");
		}

		if (request.email() != null && !request.email().isBlank() && userRepository.existsByEmail(request.email())) {
			throw new DuplicateResourceException("El email ya está registrado");
		}

		validateForeignKeys(request.municipalitieId(), request.sexoId(), request.typeDocumentId());

		People people = People.builder()
				.names(request.names())
				.surnames(request.surnames())
				.phone(request.phone())
				.email(request.email())
				.photo((request.photo() == null || request.photo().isBlank())
						? DEFAULT_PROFILE_PHOTO_URL
						: request.photo())
				.municipalitieId(request.municipalitieId())
				.sexoId(request.sexoId())
				.typeDocumentId(request.typeDocumentId())
				.numDocument(request.numDocument())
				.status(true)
				.build();

		String rawPassword = (request.password() == null || request.password().isBlank())
				? request.numDocument()
				: request.password();
		String encodedPassword = passwordEncoder.encode(rawPassword);


		User user = User.builder()
				.people(people)
				.password(encodedPassword)
				.build();

		user = userRepository.save(user);
		if (user.getId() == null) {
			throw new IllegalStateException("No se pudo registrar el usuario (sin ID)");
		}

		sendWelcomeEmail(request.email(), request.names());

		return mapToUserResponse(user);
	}

	private void sendWelcomeEmail(String email, String names) {
		if (email == null || email.isBlank()) return;
		try {
			notificationService.sendEmail(email, "welcome", Map.of(
                    "userName", names,
                    "loginUrl", URL_FRONTEND_LOGIN+"/login"
                    ));
		} catch (Exception e) {
			log.warn("No se pudo enviar el email de bienvenida a {}: {}", email, e.getMessage());
		}
	}

	private void validateForeignKeys(Long municipalitieId, Long sexoId, Long typeDocumentId) {
		municipalityRepository.findByIdAndStatusTrue(municipalitieId)
				.orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + municipalitieId));
		sexoRepository.findByIdAndStatusTrue(sexoId)
				.orElseThrow(() -> new ResourceNotFoundException("Sexo no encontrado con ID: " + sexoId));
		typeDocumentRepository.findByIdAndStatusTrue(typeDocumentId)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado con ID: " + typeDocumentId));
	}

	@Transactional(readOnly = true)
	public AuthGymResponse loginWithGym(LoginWithGymRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.password())
		);

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy h:mm a");
        String formatterDate = ahora.format(formatter);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userDetails.getUser();

		var userGym = usersGymRepository.findByIdAndStatusTrue(request.userGymId())
				.orElseThrow(() -> new ResourceNotFoundException("Relacion user_gym no encontrada con ID: " + request.userGymId()));
		if (user.getId() == null || !user.getId().equals(userGym.getUserId())) {
			throw new ResourceNotFoundException("Relacion user_gym no valida para el usuario");
		}

		UserGymPermissionsResponse userGymPermissions = roleService.getRolesAndPermissionsForUserGym(request.userGymId());
		List<String> permissions = userGymPermissions.permissions();
		GymSubscriptionResponse subscription = gymSubscriptionService.getCurrentByGymId(userGym.getGymId());

		UserDetailsImpl scopedDetails = new UserDetailsImpl(user);
		var authorities = permissions.stream()
				.map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());

		UsernamePasswordAuthenticationToken scopedAuth =
				new UsernamePasswordAuthenticationToken(scopedDetails, null, authorities);
		// Adjunta el contexto de gym para que la auditoría atribuya el login al gym/userGym correcto.
		scopedAuth.setDetails(new GymAuthenticationDetails(userGym.getGymId(), userGym.getId()));
		SecurityContextHolder.getContext().setAuthentication(scopedAuth);
		String jwt = jwtUtils.generateJwtToken(scopedAuth, userGym.getGymId(), userGym.getId());

		List<GymInfoResponse> gyms = user.getId() == null
				? List.of()
				: userRepository.findGymsByUserId(user.getId()).stream()
						.filter(g -> request.userGymId().equals(g.getUserGymId()))
						.map(this::toGymInfoResponse)
						.collect(Collectors.toList());

		String names = null;
		String photo = null;
		if (user.getPeople() != null) {
			names = user.getPeople().getNames();
			photo = user.getPeople().getPhoto();
		}

        try {
            notificationService.sendEmail(user.getPeople().getEmail(), "login_notification", Map.of(
                    "userName", names,
                    "gymName", gyms.get(0).name(),
                    "date", formatterDate
            ));
        } catch (Exception e) {
            log.warn("No se pudo enviar el email de inicio de sesion a {}: {}", user.getPeople().getEmail(), e.getMessage());
        }

		return new AuthGymResponse(
				jwt,
				names,
				user.getId(),
				photo,
				userGymPermissions.roles(),
				permissions,
				subscription.active(),
				subscription.endDate(),
				gyms
		);
	}

	@Transactional(readOnly = true)
	public CurrentUserResponse getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String login = authentication.getName();

		User user = userRepository.findByLoginWithRolesAndPermissions(login)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

		return mapToCurrentUserResponse(user);
	}

	private CurrentUserResponse mapToCurrentUserResponse(User user) {
		java.util.Set<String> roles = java.util.Set.of();
		java.util.Set<String> permissions = java.util.Set.of();

		String login = null;
		String firstName = null;
		String lastName = null;
		LocalDateTime createdAt = null;
		Boolean enabled = Boolean.TRUE;
		if (user.getPeople() != null) {
			login = (user.getPeople().getEmail() != null && !user.getPeople().getEmail().isBlank())
					? user.getPeople().getEmail()
					: user.getPeople().getNumDocument();
			firstName = user.getPeople().getNames();
			lastName = user.getPeople().getSurnames();
			createdAt = user.getPeople().getCreatedAt();
			enabled = user.getPeople().getStatus();
		}

		List<GymInfoResponse> gyms = user.getId() == null
				? List.of()
				: userRepository.findGymsByUserId(user.getId()).stream()
						.map(this::toGymInfoResponse)
						.collect(Collectors.toList());

		return new CurrentUserResponse(
				user.getId(),
				login,
				firstName,
				lastName,
				enabled,
				createdAt,
				roles,
				permissions,
				gyms
		);
	}

	private GymInfoResponse toGymInfoResponse(GymInfo gymInfo) {
		GymSubscriptionResponse subscription = gymSubscriptionService.getCurrentByGymId(gymInfo.getId());
		return new GymInfoResponse(
				gymInfo.getUserGymId(),
				gymInfo.getId(),
				gymInfo.getName(),
				gymInfo.getLogo(),
				gymInfo.getMunicipalitie(),
				subscription.active(),
				subscription.endDate()
		);
	}

	private UserResponse mapToUserResponse(User user) {
		java.util.Set<String> roles = java.util.Set.of();
		java.util.Set<String> permissions = java.util.Set.of();

		String login = null;
		String firstName = null;
		String lastName = null;
		LocalDateTime createdAt = null;
		Boolean enabled = Boolean.TRUE;
		if (user.getPeople() != null) {
			login = (user.getPeople().getEmail() != null && !user.getPeople().getEmail().isBlank())
					? user.getPeople().getEmail()
					: user.getPeople().getNumDocument();
			firstName = user.getPeople().getNames();
			lastName = user.getPeople().getSurnames();
			createdAt = user.getPeople().getCreatedAt();
			enabled = user.getPeople().getStatus();
		}

		return new UserResponse(
				user.getId(),
				login,
				firstName,
				lastName,
				enabled,
				createdAt,
				roles,
				permissions
		);
	}

}
