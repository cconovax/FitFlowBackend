package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.UserGymUserPeople;
import com.conovax.fitflow.domain.entities.UsersGym;
import com.conovax.fitflow.domain.repositories.UsersGymRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.UsersGymJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.UsersGymMapper;
import com.conovax.fitflow.infrastructure.persistence.projections.UserGymUserPeopleProjection;
import com.conovax.fitflow.infrastructure.persistence.repositories.UsersGymJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsersGymRepositoryAdapter implements UsersGymRepository {

	private final UsersGymJpaRepository jpaRepository;
	private final UsersGymMapper mapper;

	public UsersGymRepositoryAdapter(UsersGymJpaRepository jpaRepository, UsersGymMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public UsersGym save(UsersGym entity) {
		UsersGymJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(entity));
		return mapper.toDomain(saved);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public Optional<UsersGym> findById(Long id) {
		return jpaRepository.findById(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<UsersGym> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<UsersGym> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<UsersGym> findByIdAndGymIdAndStatusTrue(Long id, Long gymId) {
		return jpaRepository.findByIdAndGymIdAndStatusTrue(id, gymId)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<UsersGym> findByIdAndGymIdAndStatusFalse(Long id, Long gymId) {
		return jpaRepository.findByIdAndGymIdAndStatusFalse(id, gymId)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<UsersGym> findByUserIdAndGymId(Long userId, Long gymId) {
		return jpaRepository.findByUserIdAndGymId(userId, gymId)
				.map(mapper::toDomain);
	}

	@Override
	public List<UsersGym> findAllByUserId(Long userId) {
		return jpaRepository.findAllByUserId(userId).stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public Optional<UsersGym> findByUserIdAndGymIdAndStatusTrue(Long userId, Long gymId) {
		return jpaRepository.findByUserIdAndGymId(userId, gymId)
				.filter(entity -> Boolean.TRUE.equals(entity.getStatus()))
				.map(mapper::toDomain);
	}

	@Override
	public boolean existsByUserIdAndGymId(Long userId, Long gymId) {
		return jpaRepository.existsByUserIdAndGymId(userId, gymId);
	}

	@Override
	public boolean existsByUserIdAndGymIdAndStatusTrue(Long userId, Long gymId) {
		return jpaRepository.existsByUserIdAndGymIdAndStatusTrue(userId, gymId);
	}

	@Override
	public Page<UserGymUserPeople> findUsersByGymId(Long gymId, String search, Pageable pageable) {
		return jpaRepository.findUsersByGymId(gymId, search, pageable)
				.map(this::toUserGymUserPeople);
	}

	@Override
	public Optional<UserGymUserPeople> findUserGymPeopleById(Long userGymId) {
		UserGymUserPeopleProjection projection = jpaRepository.findUserGymPeopleById(userGymId);
		return Optional.ofNullable(projection)
				.map(this::toUserGymUserPeople);
	}

	@Override
	public List<UserGymUserPeople> findTrainersByGymId(Long gymId, String search) {
		return jpaRepository.findTrainersByGymId(gymId, search).stream()
				.map(this::toUserGymUserPeople)
				.toList();
	}

	private UserGymUserPeople toUserGymUserPeople(UserGymUserPeopleProjection projection) {
		return UserGymUserPeople.builder()
				.userGymId(projection.getUserGymId())
				.userId(projection.getUserId())
				.userGymStatus(projection.getUserGymStatus())
				.peopleId(projection.getPeopleId())
				.numDocument(projection.getNumDocument())
				.email(projection.getEmail())
				.names(projection.getNames())
				.surnames(projection.getSurnames())
				.phone(projection.getPhone())
				.build();
	}
}
