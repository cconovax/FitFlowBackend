package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.response.UserGymUserResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.pagination.PaginationUtils;
import com.conovax.fitflow.domain.entities.UserGymUserPeople;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.GymRepository;
import com.conovax.fitflow.domain.repositories.UsersGymRepository;
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
