package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.UserGymMembershipActiveUser;
import com.conovax.sexbody.infrastructure.persistence.projections.UserGymMembershipActiveProjection;
import com.conovax.sexbody.domain.entities.UserGymMembership;
import com.conovax.sexbody.domain.repositories.UserGymMembershipRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.UserGymMembershipJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.UserGymMembershipMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.UserGymMembershipJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserGymMembershipRepositoryAdapter implements UserGymMembershipRepository {

	private final UserGymMembershipJpaRepository jpaRepository;
	private final UserGymMembershipMapper mapper;

	public UserGymMembershipRepositoryAdapter(UserGymMembershipJpaRepository jpaRepository, UserGymMembershipMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public List<UserGymMembership> findAllByUserGymIdOrderByStartDateDescIdDesc(Long userGymId) {
		return jpaRepository.findAllByUserGymIdOrderByStartDateDescIdDesc(userGymId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<UserGymMembership> findById(Long id) {
		return jpaRepository.findById(id)
				.map(mapper::toDomain);
	}

	@Override
	public boolean existsActiveMembershipByUserGymId(Long userGymId, LocalDate today) {
		return jpaRepository.existsByUserGymIdAndStatusTrueAndEndDateGreaterThanEqual(userGymId, today);
	}

	@Override
	public boolean existsActiveMembershipByUserGymIdAndMembershipId(Long userGymId, Long membershipId, LocalDate today) {
		return jpaRepository.existsByUserGymIdAndMembershipIdAndStatusTrueAndEndDateGreaterThanEqual(
				userGymId,
				membershipId,
				today
		);
	}

	@Override
	public List<UserGymMembershipActiveUser> findActiveUsersByMembershipId(Long membershipId, LocalDate today) {
		return jpaRepository.findActiveUsersByMembershipId(membershipId, today).stream()
				.map(this::toActiveUser)
				.collect(Collectors.toList());
	}

	@Override
	public UserGymMembership save(UserGymMembership entity) {
		UserGymMembershipJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(entity));
		return mapper.toDomain(saved);
	}

	private UserGymMembershipActiveUser toActiveUser(
			UserGymMembershipActiveProjection projection
	) {
		return UserGymMembershipActiveUser.builder()
				.userGymMembershipId(projection.getUserGymMembershipId())
				.userGymId(projection.getUserGymId())
				.membershipId(projection.getMembershipId())
				.startDate(projection.getStartDate())
				.endDate(projection.getEndDate())
				.status(projection.getStatus())
				.userId(projection.getUserId())
				.peopleId(projection.getPeopleId())
				.names(projection.getNames())
				.surnames(projection.getSurnames())
				.email(projection.getEmail())
				.phone(projection.getPhone())
				.build();
	}
}
