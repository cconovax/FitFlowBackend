package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.ChangePasswordRequest;
import com.conovax.sexbody.application.dto.request.UpdateProfileRequest;
import com.conovax.sexbody.application.dto.response.GymInfoResponse;
import com.conovax.sexbody.application.dto.response.GymSubscriptionResponse;
import com.conovax.sexbody.application.dto.response.LoginGymsResponse;
import com.conovax.sexbody.application.dto.response.PeopleResponse;
import com.conovax.sexbody.application.dto.response.UserResponse;
import com.conovax.sexbody.domain.entities.GymInfo;
import com.conovax.sexbody.domain.entities.People;
import com.conovax.sexbody.domain.entities.User;
import com.conovax.sexbody.domain.exceptions.DuplicateResourceException;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.MunicipalityRepository;
import com.conovax.sexbody.domain.repositories.PeopleRepository;
import com.conovax.sexbody.domain.repositories.SexoRepository;
import com.conovax.sexbody.domain.repositories.TypeDocumentRepository;
import com.conovax.sexbody.domain.repositories.UserRepository;
import com.conovax.sexbody.infrastructure.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PeopleRepository peopleRepository;
	private final MunicipalityRepository municipalityRepository;
	private final SexoRepository sexoRepository;
	private final TypeDocumentRepository typeDocumentRepository;
	private final PasswordEncoder passwordEncoder;
	private final GymSubscriptionService gymSubscriptionService;

	@Transactional(readOnly = true)
	public LoginGymsResponse getGymsByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
		if (user.getPeople() == null || !Boolean.TRUE.equals(user.getPeople().getStatus())) {
			throw new ResourceNotFoundException("Usuario no encontrado con email: " + email);
		}

		List<GymInfoResponse> gyms = user.getId() == null
				? List.of()
				: userRepository.findGymsByUserId(user.getId()).stream()
						.map(this::toGymInfoResponse)
						.collect(Collectors.toList());

		return new LoginGymsResponse(gyms);
	}

	@Transactional(readOnly = true)
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream()
				.filter(user -> user.getPeople() != null && Boolean.TRUE.equals(user.getPeople().getStatus()))
				.map(this::mapToUserResponse)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public UserResponse getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
		if (user.getPeople() == null || !Boolean.TRUE.equals(user.getPeople().getStatus())) {
			throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
		}
		return mapToUserResponse(user);
	}

	@Transactional
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
		}
		userRepository.deleteById(id);
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

	// ── Profile (authenticated user) ────────────────────────────────

	@Transactional(readOnly = true)
	public PeopleResponse getMyProfile() {
		User user = resolveCurrentUser();
		People people = requirePeople(user);
		return toProfileResponse(people);
	}

	@Transactional
	public PeopleResponse updateMyProfile(UpdateProfileRequest request) {
		User user = resolveCurrentUser();
		People people = requirePeople(user);

		if (!people.getNumDocument().equals(request.numDocument())
				&& peopleRepository.existsByNumDocumentAndStatusTrueAndIdNot(request.numDocument(), people.getId())) {
			throw new DuplicateResourceException("El número de documento ya está registrado");
		}
		if (request.email() != null && !request.email().isBlank()
				&& !request.email().equalsIgnoreCase(people.getEmail())
				&& peopleRepository.existsByEmailAndStatusTrueAndIdNot(request.email(), people.getId())) {
			throw new DuplicateResourceException("El email ya está registrado");
		}

		municipalityRepository.findByIdAndStatusTrue(request.municipalitieId())
				.orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + request.municipalitieId()));
		sexoRepository.findByIdAndStatusTrue(request.sexoId())
				.orElseThrow(() -> new ResourceNotFoundException("Sexo no encontrado con ID: " + request.sexoId()));
		typeDocumentRepository.findByIdAndStatusTrue(request.typeDocumentId())
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado con ID: " + request.typeDocumentId()));

		People updated = people.toBuilder()
				.names(request.names())
				.surnames(request.surnames())
				.phone(request.phone())
				.email(request.email())
				.numDocument(request.numDocument())
				.typeDocumentId(request.typeDocumentId())
				.sexoId(request.sexoId())
				.municipalitieId(request.municipalitieId())
				.build();

		return toProfileResponse(peopleRepository.save(updated));
	}

	@Transactional
	public void changeMyPassword(ChangePasswordRequest request) {
		User user = resolveCurrentUser();

		if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
			throw new BadCredentialsException("La contraseña actual es incorrecta");
		}

		User updated = user.toBuilder()
				.password(passwordEncoder.encode(request.newPassword()))
				.build();

		userRepository.save(updated);
	}

	private User resolveCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof UserDetailsImpl userDetails)) {
			throw new ResourceNotFoundException("Usuario no autenticado");
		}
		Long userId = userDetails.getUser().getId();
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + userId));
	}

	private People requirePeople(User user) {
		if (user.getPeople() == null) {
			throw new ResourceNotFoundException("Datos personales no encontrados para el usuario");
		}
		return user.getPeople();
	}

	private PeopleResponse toProfileResponse(People p) {
		return new PeopleResponse(
				p.getId(),
				p.getNames(),
				p.getSurnames(),
				p.getPhone(),
				p.getEmail(),
				p.getPhoto(),
				p.getMunicipalitieId(),
				p.getSexoId(),
				p.getTypeDocumentId(),
				p.getNumDocument(),
				p.getStatus(),
				p.getCreatedAt(),
				p.getUpdatedAt()
		);
	}

	// ────────────────────────────────────────────────────────────────

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
