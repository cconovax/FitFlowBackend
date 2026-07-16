package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.MembershipCoachRequest;
import com.conovax.sexbody.application.dto.response.MembershipActiveUserResponse;
import com.conovax.sexbody.application.dto.response.MembershipCoachResponse;
import com.conovax.sexbody.domain.entities.Membership;
import com.conovax.sexbody.domain.entities.MembershipCoach;
import com.conovax.sexbody.domain.entities.Role;
import com.conovax.sexbody.domain.entities.UserGymMembershipActiveUser;
import com.conovax.sexbody.domain.entities.UserGymUserPeople;
import com.conovax.sexbody.domain.entities.UsersGym;
import com.conovax.sexbody.domain.exceptions.DuplicateResourceException;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.MembershipCoachRepository;
import com.conovax.sexbody.domain.repositories.MembershipRepository;
import com.conovax.sexbody.domain.repositories.UserGymMembershipRepository;
import com.conovax.sexbody.domain.repositories.UserRoleRepository;
import com.conovax.sexbody.domain.repositories.UsersGymRepository;
import com.conovax.sexbody.domain.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipCoachService {

	private final MembershipCoachRepository repository;
	private final MembershipRepository membershipRepository;
	private final UsersGymRepository usersGymRepository;
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;
	private final UserGymMembershipRepository userGymMembershipRepository;

	@Transactional(readOnly = true)
	public List<MembershipCoachResponse> getAllByGymId(Long gymId) {
		return repository.findAllByGymId(gymId).stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<MembershipCoachResponse> getAllByCoachId(Long coachId) {
		return repository.findAllByCoachId(coachId).stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public MembershipCoachResponse getById(Long id) {
		MembershipCoach entity = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada con ID: " + id));
		return toResponse(entity);
	}

	@Transactional
	public MembershipCoachResponse create(MembershipCoachRequest request) {
		Membership membership = membershipRepository.findByIdAndStatusTrue(request.membershipId())
				.orElseThrow(() -> new ResourceNotFoundException("Membresía no encontrada con ID: " + request.membershipId()));

		UsersGym coachRelation = usersGymRepository.findByIdAndStatusTrue(request.coachId())
				.orElseThrow(() -> new ResourceNotFoundException("Entrenador no encontrado con ID: " + request.coachId()));

		assertCoachIsTrainer(coachRelation.getId());

		Long membershipGymId = membership.getGymId();
		if (membershipGymId != null && coachRelation.getGymId() != null
				&& !membershipGymId.equals(coachRelation.getGymId())) {
			throw new IllegalArgumentException("El entrenador no pertenece al gym de la membresía");
		}

		if (repository.existsByMembershipIdAndCoachIdAndStatusTrue(request.membershipId(), request.coachId())) {
			throw new DuplicateResourceException("La membresía ya está asignada a este entrenador");
		}

		MembershipCoach entity = MembershipCoach.builder()
				.membershipId(membership.getId())
				.coachId(coachRelation.getId())
				.status(request.status() != null ? request.status() : Boolean.TRUE)
				.build();

		return toResponse(repository.save(entity));
	}

	@Transactional
	public void deleteLogical(Long id) {
		MembershipCoach entity = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada con ID: " + id));
		repository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		MembershipCoach entity = repository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada con ID: " + id));
		repository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Asignación no encontrada con ID: " + id);
		}
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<MembershipActiveUserResponse> getActiveUsersByMembership(Long membershipId, Long coachId) {
		Membership membership = membershipRepository.findByIdAndStatusTrue(membershipId)
				.orElseThrow(() -> new ResourceNotFoundException("Membresía no encontrada con ID: " + membershipId));

		if (!repository.existsByMembershipIdAndCoachIdAndStatusTrue(membershipId, coachId)) {
			throw new IllegalArgumentException("El entrenador no está asignado a esta membresía");
		}

		List<UserGymMembershipActiveUser> activeUsers = userGymMembershipRepository
				.findActiveUsersByMembershipId(membership.getId(), LocalDate.now());

		return activeUsers.stream()
				.map(user -> new MembershipActiveUserResponse(
						user.getUserGymMembershipId(),
						user.getUserGymId(),
						user.getMembershipId(),
						formatName(user),
						user.getEmail(),
						user.getPhone(),
						user.getStartDate(),
						user.getEndDate(),
						user.getStatus()
				))
				.toList();
	}

	private void assertCoachIsTrainer(Long coachId) {
		Role trainerRole = roleRepository.findByNameAndStatusTrue("Entrenador")
				.orElseThrow(() -> new ResourceNotFoundException("Rol Entrenador no encontrado"));

		if (!userRoleRepository.existsByUserGymIdAndRoleId(coachId, trainerRole.getId())) {
			throw new IllegalArgumentException("El usuario no tiene rol de entrenador");
		}
	}

	private MembershipCoachResponse toResponse(MembershipCoach entity) {
		String membershipName = null;
		Membership membership = membershipRepository.findByIdAndStatusTrue(entity.getMembershipId()).orElse(null);
		if (membership != null) {
			membershipName = membership.getName();
		}

		String coachName = null;
		UserGymUserPeople coach = usersGymRepository.findUserGymPeopleById(entity.getCoachId()).orElse(null);
		if (coach != null) {
			coachName = String.format("%s %s",
					coach.getNames() != null ? coach.getNames() : "",
					coach.getSurnames() != null ? coach.getSurnames() : ""
			).trim();
			if (coachName.isBlank()) {
				coachName = coach.getEmail();
			}
		}

		return new MembershipCoachResponse(
				entity.getId(),
				entity.getMembershipId(),
				membershipName,
				entity.getCoachId(),
				coachName,
				entity.getStatus()
		);
	}

	private String formatName(UserGymMembershipActiveUser user) {
		String name = String.format("%s %s",
				user.getNames() != null ? user.getNames() : "",
				user.getSurnames() != null ? user.getSurnames() : ""
		).trim();
		if (name.isBlank()) {
			return user.getEmail();
		}
		return name;
	}
}
