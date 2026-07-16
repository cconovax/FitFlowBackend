package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.MembershipResultRequest;
import com.conovax.sexbody.application.dto.response.MembershipResultResponse;
import com.conovax.sexbody.domain.entities.MembershipRating;
import com.conovax.sexbody.domain.entities.MembershipResult;
import com.conovax.sexbody.domain.entities.UserGymMembership;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.MembershipCoachRepository;
import com.conovax.sexbody.domain.repositories.MembershipRatingRepository;
import com.conovax.sexbody.domain.repositories.MembershipResultRepository;
import com.conovax.sexbody.domain.repositories.UserGymMembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MembershipResultService {

	private final MembershipResultRepository repository;
	private final UserGymMembershipRepository userGymMembershipRepository;
	private final MembershipCoachRepository membershipCoachRepository;
	private final MembershipRatingRepository membershipRatingRepository;

	@Transactional(readOnly = true)
	public MembershipResultResponse getByUserGymMembershipId(Long userGymMembershipId) {
		MembershipResult entity = repository.findByUserGymMembershipId(userGymMembershipId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Resultado no encontrado para la membresía asignada: " + userGymMembershipId
				));
		return toResponse(entity);
	}

	@Transactional
	public MembershipResultResponse createOrUpdate(MembershipResultRequest request) {
		UserGymMembership relation = userGymMembershipRepository.findById(request.userGymMembershipId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Relación usuario-membresía no encontrada con ID: " + request.userGymMembershipId()
				));

		validateCoachAssignment(relation.getMembershipId(), request.coachId());

		MembershipResult entity = repository.findByUserGymMembershipId(relation.getId())
				.orElse(null);

		LocalDate createdAt = request.createdAt() != null ? request.createdAt() : LocalDate.now();

		if (entity == null) {
			entity = MembershipResult.builder()
					.userGymMembershipId(relation.getId())
					.coachId(request.coachId())
					.startWeight(request.startWeight())
					.endWeight(request.endWeight())
					.startFat(request.startFat())
					.endFat(request.endFat())
					.startMuscleMass(request.startMuscleMass())
					.endMuscleMass(request.endMuscleMass())
					.createdAt(createdAt)
					.status(request.status() != null ? request.status() : Boolean.TRUE)
					.build();
		} else {
			entity = entity.toBuilder()
					.coachId(request.coachId())
					.startWeight(request.startWeight())
					.endWeight(request.endWeight())
					.startFat(request.startFat())
					.endFat(request.endFat())
					.startMuscleMass(request.startMuscleMass())
					.endMuscleMass(request.endMuscleMass())
					.createdAt(createdAt)
					.status(request.status() != null ? request.status() : entity.getStatus())
					.build();
		}

		return toResponse(repository.save(entity));
	}

	@Transactional
	public MembershipResultResponse calculateFromRatings(Long userGymMembershipId, Long coachId) {
		UserGymMembership relation = userGymMembershipRepository.findById(userGymMembershipId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Relaci\u00f3n usuario-membres\u00eda no encontrada con ID: " + userGymMembershipId
				));

		validateCoachAssignment(relation.getMembershipId(), coachId);

		var ratingsDesc = membershipRatingRepository
				.findAllByUserGymMembershipIdOrderByDateDescIdDesc(userGymMembershipId);

		if (ratingsDesc.isEmpty()) {
			throw new IllegalArgumentException(
					"El usuario no tiene evaluaciones registradas para calcular el resultado"
			);
		}

		MembershipRating newest = ratingsDesc.get(0);
		MembershipRating oldest = membershipRatingRepository
				.findOldestByUserGymMembershipId(userGymMembershipId)
				.orElse(newest);

		MembershipResult entity = repository.findByUserGymMembershipId(userGymMembershipId)
				.orElse(null);

		if (entity == null) {
			entity = MembershipResult.builder()
					.userGymMembershipId(relation.getId())
					.coachId(coachId)
					.startWeight(oldest.getWeight())
					.endWeight(newest.getWeight())
					.startFat(oldest.getPorcentageFat())
					.endFat(newest.getPorcentageFat())
					.startMuscleMass(oldest.getMuscleMass())
					.endMuscleMass(newest.getMuscleMass())
					.createdAt(LocalDate.now())
					.status(Boolean.TRUE)
					.build();
		} else {
			entity = entity.toBuilder()
					.coachId(coachId)
					.startWeight(oldest.getWeight())
					.endWeight(newest.getWeight())
					.startFat(oldest.getPorcentageFat())
					.endFat(newest.getPorcentageFat())
					.startMuscleMass(oldest.getMuscleMass())
					.endMuscleMass(newest.getMuscleMass())
					.createdAt(LocalDate.now())
					.build();
		}

		return toResponse(repository.save(entity));
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

	private MembershipResultResponse toResponse(MembershipResult entity) {
		return new MembershipResultResponse(
				entity.getId(),
				entity.getUserGymMembershipId(),
				entity.getCoachId(),
				entity.getStartWeight(),
				entity.getEndWeight(),
				entity.getStartFat(),
				entity.getEndFat(),
				entity.getStartMuscleMass(),
				entity.getEndMuscleMass(),
				entity.getCreatedAt(),
				entity.getStatus()
		);
	}
}
