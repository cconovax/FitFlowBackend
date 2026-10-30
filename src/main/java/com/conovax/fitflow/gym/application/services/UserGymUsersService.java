package com.conovax.fitflow.gym.application.services;

import com.conovax.fitflow.gym.application.dto.response.UserGymContact;
import com.conovax.fitflow.gym.application.dto.response.UserGymUserResponse;
import com.conovax.fitflow.shared.application.pagination.PageResponse;
import com.conovax.fitflow.shared.application.pagination.PaginationUtils;
import com.conovax.fitflow.gym.domain.entities.UserGymUserPeople;
import com.conovax.fitflow.shared.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.gym.domain.repositories.GymRepository;
import com.conovax.fitflow.gym.domain.repositories.UsersGymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserGymUsersService {

	private final GymRepository gymRepository;
	private final UsersGymRepository usersGymRepository;

	@Transactional(readOnly = true)
	public PageResponse<UserGymUserResponse> getUsersByGymId(Long gymId, Integer page, Integer size, String search) {
		gymRepository.findByIdAndStatusTrue(gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + gymId));

		Pageable pageable = PaginationUtils.pageable(page, size);
		Page<UserGymUserPeople> relations = usersGymRepository.findUsersByGymId(gymId, search, pageable);
		return PaginationUtils.map(relations, this::toResponse);
	}

	@Transactional(readOnly = true)
	public java.util.List<UserGymUserResponse> getTrainersByGymId(Long gymId, String search) {
		gymRepository.findByIdAndStatusTrue(gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + gymId));

		return usersGymRepository.findTrainersByGymId(gymId, search).stream()
				.map(this::toResponse)
				.toList();
	}

	/** user_gyms activos del gym con rol activo Entrenador o Administrador, en formato de contacto mínimo. */
	@Transactional(readOnly = true)
	public java.util.List<UserGymContact> getTrainerOrAdminContacts(Long gymId) {
		return usersGymRepository.findActiveTrainersOrAdminsByGymId(gymId).stream()
				.map(p -> new UserGymContact(p.getUserGymId(),
						((p.getNames() == null ? "" : p.getNames()) + " "
								+ (p.getSurnames() == null ? "" : p.getSurnames())).trim(),
						p.getEmail()))
				.toList();
	}

	/** Resuelve nombre completo y email de user_gyms del gym indicado; los ids de otro gym se omiten. */
	@Transactional(readOnly = true)
	public java.util.Map<Long, UserGymContact> getContactsByUserGymIds(Long gymId, java.util.Collection<Long> userGymIds) {
		java.util.Map<Long, UserGymContact> contacts = new java.util.HashMap<>();
		if (gymId == null || userGymIds == null) {
			return contacts;
		}
		for (Long userGymId : new java.util.LinkedHashSet<>(userGymIds)) {
			if (userGymId == null) continue;
			boolean sameGym = usersGymRepository.findById(userGymId)
					.map(ug -> gymId.equals(ug.getGymId()))
					.orElse(false);
			if (!sameGym) continue;
			usersGymRepository.findUserGymPeopleById(userGymId).ifPresent(p -> {
				String fullName = ((p.getNames() == null ? "" : p.getNames()) + " "
						+ (p.getSurnames() == null ? "" : p.getSurnames())).trim();
				contacts.put(userGymId, new UserGymContact(userGymId, fullName, p.getEmail()));
			});
		}
		return contacts;
	}

	private UserGymUserResponse toResponse(UserGymUserPeople p) {
		return new UserGymUserResponse(
				p.getPeopleId(),
				p.getNumDocument(),
				p.getNames(),
				p.getSurnames(),
				p.getPhone(),
				p.getEmail(),
				p.getUserGymStatus(),
				p.getUserId(),
				p.getUserGymId()
		);
	}
}
