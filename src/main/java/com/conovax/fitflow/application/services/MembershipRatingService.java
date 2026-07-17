package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.MembershipRatingRequest;
import com.conovax.fitflow.application.dto.response.MembershipRatingResponse;
import com.conovax.fitflow.domain.entities.MembershipRating;
import com.conovax.fitflow.domain.entities.UserGymMembership;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.MembershipCoachRepository;
import com.conovax.fitflow.domain.repositories.MembershipRatingRepository;
import com.conovax.fitflow.domain.repositories.UserGymMembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipRatingService {

	private final MembershipRatingRepository repository;
	private final UserGymMembershipRepository userGymMembershipRepository;
	private final MembershipCoachRepository membershipCoachRepository;

	@Transactional(readOnly = true)
	public List<MembershipRatingResponse> getAllByUserGymMembershipId(Long userGymMembershipId) {
		return repository.findAllByUserGymMembershipIdOrderByDateDescIdDesc(userGymMembershipId).stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional
	public MembershipRatingResponse create(MembershipRatingRequest request) {
		UserGymMembership relation = userGymMembershipRepository.findById(request.userGymMembershipId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Relación usuario-membresía no encontrada con ID: " + request.userGymMembershipId()
				));

		if (relation.getEndDate() != null && relation.getEndDate().isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("La membresía ha vencido. No se pueden registrar evaluaciones.");
		}

		validateCoachAssignment(relation.getMembershipId(), request.coachId());

		MembershipRating entity = MembershipRating.builder()
				.userGymMembershipId(relation.getId())
				.coachId(request.coachId())
				.date(LocalDate.now())
				.weight(request.weight())
				.observation(request.observation())
				.porcentageFat(request.porcentageFat())
				.muscleMass(request.muscleMass())
				.status(request.status() != null ? request.status() : Boolean.TRUE)
				.build();

		return toResponse(repository.save(entity));
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Evaluación no encontrada con ID: " + id);
		}
		repository.deleteById(id);
	}

	private void validateCoachAssignment(Long membershipId, Long coachId) {
		if (membershipId == null || coachId == null) {
			throw new IllegalArgumentException("membershipId y coachId son obligatorios");
		}

		boolean assigned = membershipCoachRepository.existsByMembershipIdAndCoachIdAndStatusTrue(membershipId, coachId);
		if (!assigned) {
			throw new IllegalArgumentException("El entrenador no está asignado a esta membresía");
		}
	}

	private MembershipRatingResponse toResponse(MembershipRating entity) {
		return new MembershipRatingResponse(
				entity.getId(),
				entity.getUserGymMembershipId(),
				entity.getCoachId(),
				entity.getDate(),
				entity.getWeight(),
				entity.getObservation(),
				entity.getPorcentageFat(),
				entity.getMuscleMass(),
				entity.getStatus()
		);
	}
}
