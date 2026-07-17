package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.User;
import com.conovax.fitflow.domain.entities.GymInfo;
import com.conovax.fitflow.domain.repositories.UserRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.UserJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.UserMapper;
import com.conovax.fitflow.infrastructure.persistence.projections.GymInfoProjection;
import com.conovax.fitflow.infrastructure.persistence.repositories.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryAdapter implements UserRepository {

	private final UserJpaRepository jpaRepository;
	private final UserMapper mapper;

	public UserRepositoryAdapter(UserJpaRepository jpaRepository, UserMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public User save(User user) {
		UserJpaEntity jpaEntity = mapper.toJpaEntity(user);
		UserJpaEntity saved = jpaRepository.save(jpaEntity);
		return mapper.toDomain(saved);
	}

	@Override
	public Optional<User> findById(Long id) {
		return jpaRepository.findById(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return jpaRepository.findByEmailWithRolesAndPermissions(email)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<User> findByNumDocument(String numDocument) {
		return jpaRepository.findByPeople_NumDocument(numDocument)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<User> findByEmailWithRolesAndPermissions(String email) {
		return jpaRepository.findByEmailWithRolesAndPermissions(email)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<User> findByLoginWithRolesAndPermissions(String login) {
		return jpaRepository.findByLoginWithRolesAndPermissions(login)
				.map(mapper::toDomain);
	}

	@Override
	public List<GymInfo> findGymsByUserId(Long userId) {
		return jpaRepository.findGymsByUserId(userId).stream()
				.map(this::toGymInfo)
				.collect(Collectors.toList());
	}

	private GymInfo toGymInfo(GymInfoProjection projection) {
		return GymInfo.builder()
				.userGymId(projection.getUserGymId())
				.id(projection.getId())
				.name(projection.getName())
				.logo(projection.getLogo())
				.municipalitie(projection.getMunicipalitie())
				.build();
	}

	@Override
	public List<User> findAll() {
		return jpaRepository.findAll().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public boolean existsByEmail(String email) {
		return jpaRepository.existsByPeople_Email(email);
	}

	@Override
	public boolean existsByNumDocument(String numDocument) {
		return jpaRepository.existsByPeople_NumDocument(numDocument);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public long count() {
		return jpaRepository.count();
	}

	@Override
	public boolean existsById(Long id) {
		return jpaRepository.existsById(id);
	}
}
