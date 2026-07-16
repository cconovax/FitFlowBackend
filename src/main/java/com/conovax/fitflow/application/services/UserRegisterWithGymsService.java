package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.RegisterRequest;
import com.conovax.sexbody.application.dto.request.UserRegisterWithGymsRequest;
import com.conovax.sexbody.application.dto.response.UserResponse;
import com.conovax.sexbody.domain.entities.UsersGym;
import com.conovax.sexbody.domain.entities.UsersGymRole;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.RoleRepository;
import com.conovax.sexbody.domain.repositories.GymRepository;
import com.conovax.sexbody.domain.repositories.UserRoleRepository;
import com.conovax.sexbody.domain.repositories.UsersGymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserRegisterWithGymsService {

	private final AuthService authService;
	private final GymRepository gymRepository;
	private final UsersGymRepository usersGymRepository;
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;

	@Transactional
	public UserResponse register(UserRegisterWithGymsRequest request) {
		if (request.gymIds() == null || request.gymIds().isEmpty()) {
			throw new IllegalArgumentException("Debe enviar al menos un gymId");
		}
		if (request.roleIds() == null || request.roleIds().isEmpty()) {
			throw new IllegalArgumentException("Debe enviar al menos un roleId");
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
				request.password(),
				request.roleIds()
		);

		UserResponse created = authService.register(authRegister);
		if (created == null || created.id() == null) {
			throw new IllegalStateException("No se pudo registrar el usuario");
		}
		Long userId = created.id();

		List<Long> roleIds = request.roleIds().stream()
				.filter(Objects::nonNull)
				.distinct()
				.toList();
		for (Long roleId : roleIds) {
			roleRepository.findById(roleId)
					.orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + roleId));
		}

		for (Long gymId : request.gymIds()) {
			gymRepository.findByIdAndStatusTrue(gymId)
					.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + gymId));

			UsersGym relation = usersGymRepository.findByUserIdAndGymId(userId, gymId)
					.map(existing -> existing.toBuilder()
							.fingerprint(null)
							.status(true)
							.build())
					.orElseGet(() -> UsersGym.builder()
							.userId(userId)
							.gymId(gymId)
							.fingerprint(null)
							.status(true)
							.build());

			UsersGym saved = usersGymRepository.save(relation);
			Long userGymId = saved.getId();
			if (userGymId != null) {
				for (Long roleId : roleIds) {
					if (!userRoleRepository.existsByUserGymIdAndRoleId(userGymId, roleId)) {
						userRoleRepository.save(UsersGymRole.builder()
								.userGymId(userGymId)
								.roleId(roleId)
								.build());
					}
				}
			}
		}

		return created;
	}
}
