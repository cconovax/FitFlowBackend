package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.GymUserRegisterRequest;
import com.conovax.sexbody.application.dto.request.GymUserRelationRequest;
import com.conovax.sexbody.application.dto.request.RegisterRequest;
import com.conovax.sexbody.application.dto.response.GymUserRelationLookupResponse;
import com.conovax.sexbody.application.dto.response.UserResponse;
import com.conovax.sexbody.domain.entities.UsersGym;
import com.conovax.sexbody.domain.entities.UsersGymRole;
import com.conovax.sexbody.domain.exceptions.DuplicateResourceException;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.RoleRepository;
import com.conovax.sexbody.domain.repositories.GymRepository;
import com.conovax.sexbody.domain.repositories.UserRepository;
import com.conovax.sexbody.domain.repositories.UserRoleRepository;
import com.conovax.sexbody.domain.repositories.UsersGymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GymUserRegisterService {

	private final AuthService authService;
	private final GymRepository gymRepository;
	private final UserRepository userRepository;
	private final UsersGymRepository usersGymRepository;
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;

	@Transactional
	public UserResponse registerUserInGym(Long gymId, GymUserRegisterRequest request) {
		var gym = gymRepository.findById(gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + gymId));
		if (!Boolean.TRUE.equals(gym.getStatus())) {
			throw new ResourceNotFoundException("Gym no encontrado con ID: " + gymId);
		}

		RegisterRequest authRegister = new RegisterRequest(
				request.email(),
				request.names(),
				request.surnames(),
				request.phone(),
				request.photo(),
				request.numDocument(),
				request.municipalitieId(),
				request.sexoId(),
				request.typeDocumentId(),
				null,
				request.roleIds()
		);

		UserResponse created = authService.register(authRegister);
		if (created == null || created.id() == null) {
			throw new IllegalStateException("No se pudo registrar el usuario");
		}

		if (usersGymRepository.existsByUserIdAndGymIdAndStatusTrue(created.id(), gymId)) {
			throw new DuplicateResourceException("El usuario ya está referenciado a este gym");
		}
		byte[] fingerprint = request.fingerprint();

		UsersGym relation = UsersGym.builder()
				.userId(created.id())
				.gymId(gymId)
				.fingerprint(fingerprint)
				.status(true)
				.build();

		UsersGym saved = usersGymRepository.save(relation);
		Long userGymId = saved.getId();
		if (userGymId != null && request.roleIds() != null && !request.roleIds().isEmpty()) {
			List<Long> roleIds = request.roleIds().stream()
					.filter(Objects::nonNull)
					.distinct()
					.toList();

			for (Long roleId : roleIds) {
				roleRepository.findById(roleId)
						.orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + roleId));
				if (!userRoleRepository.existsByUserGymIdAndRoleId(userGymId, roleId)) {
					userRoleRepository.save(UsersGymRole.builder()
							.userGymId(userGymId)
							.roleId(roleId)
							.build());
				}
			}
		}
		return created;
	}

	@Transactional
	public void relationUserInGym(Long gymId, GymUserRelationRequest request) {
		Set<Long> gymIds = new LinkedHashSet<>();
		gymIds.add(gymId);
		if (request.gymIds() != null) {
			gymIds.addAll(request.gymIds());
		}
		relationUserInGyms(gymIds, request);
	}

	@Transactional
	public void relationUserInGyms(Set<Long> gymIds, GymUserRelationRequest request) {
		if (gymIds == null || gymIds.isEmpty()) {
			throw new IllegalArgumentException("Debe enviar al menos un gymId");
		}

		var user = userRepository.findByNumDocument(request.numDocument())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Usuario no encontrado con numero de cc: " + request.numDocument()
				));

		Long userId = user.getId();
		if (userId == null) {
			throw new IllegalStateException("El usuario no tiene ID");
		}

		List<Long> roleIds = request.roleIds().stream()
				.filter(Objects::nonNull)
				.distinct()
				.toList();
		for (Long roleId : roleIds) {
			roleRepository.findById(roleId)
					.orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + roleId));
		}

		for (Long gymId : gymIds) {
			var gym = gymRepository.findById(gymId)
					.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + gymId));
			if (!Boolean.TRUE.equals(gym.getStatus())) {
				throw new ResourceNotFoundException("Gimnasio "+gym.getName()+" no esta activo");
			}

			Optional<UsersGym> usersGymResult = usersGymRepository.findByUserIdAndGymId(userId, gymId);

			UsersGym relation = usersGymResult
					.map(existingRelation -> {
						// La relación ya existe: limpiamos sus roles actuales y, si estaba
						// eliminada lógicamente, la reactivamos. Nunca la creamos de nuevo.
						if (existingRelation.getId() != null) {
							userRoleRepository.deleteByUserGymId(existingRelation.getId());
						}
						if (!Boolean.TRUE.equals(existingRelation.getStatus())) {
							return usersGymRepository.save(existingRelation.toBuilder().status(true).build());
						}
						return existingRelation;
					})
					.orElseGet(() -> usersGymRepository.save(UsersGym.builder()
							.userId(userId)
							.gymId(gymId)
							.fingerprint(request.fingerprint())
							.status(true)
							.build()));

			Long userGymId = relation.getId();
			if (userGymId != null) {
				for (Long roleId : roleIds) {
					userRoleRepository.save(UsersGymRole.builder()
							.userGymId(userGymId)
							.roleId(roleId)
							.build());
				}
			}
		}
	}

	@Transactional(readOnly = true)
	public GymUserRelationLookupResponse lookupUserRelation(String numDocument) {
		var user = userRepository.findByNumDocument(numDocument)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Usuario no encontrado con numero de cc: " + numDocument
				));

		Long userId = user.getId();
		if (userId == null) {
			throw new IllegalStateException("El usuario no tiene ID");
		}

		List<Long> gymIds = usersGymRepository.findAllByUserId(userId).stream()
				.filter(relation -> Boolean.TRUE.equals(relation.getStatus()))
				.map(UsersGym::getGymId)
				.filter(Objects::nonNull)
				.distinct()
				.toList();

		List<Long> roleIds = userRoleRepository.findRoleIdsByUserId(userId).stream()
				.filter(Objects::nonNull)
				.distinct()
				.toList();

		String names = user.getPeople() != null ? user.getPeople().getNames() : null;
		String surnames = user.getPeople() != null ? user.getPeople().getSurnames() : null;

		return new GymUserRelationLookupResponse(numDocument, names, surnames, gymIds, roleIds);
	}

	@Transactional
	public void deleteLogicalUserGym(Long gymId, Long userGymId) {
		var relation = usersGymRepository.findByIdAndGymIdAndStatusTrue(userGymId, gymId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Relación user_gym no encontrada con ID: " + userGymId + " para el gym ID: " + gymId
				));

		usersGymRepository.save(relation.toBuilder().status(false).build());
	}

	@Transactional
	public void resetUserGym(Long gymId, Long userGymId) {
		var relation = usersGymRepository.findByIdAndGymIdAndStatusFalse(userGymId, gymId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Relación user_gym no encontrada o ya activa con ID: " + userGymId + " para el gym ID: " + gymId
				));

		usersGymRepository.save(relation.toBuilder().status(true).build());
	}
}
