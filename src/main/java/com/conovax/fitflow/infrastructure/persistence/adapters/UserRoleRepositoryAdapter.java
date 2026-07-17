package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.UsersGymRole;
import com.conovax.fitflow.domain.repositories.UserRoleRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.UsersGymRoleJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.UsersGymRoleMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.UserRoleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleRepositoryAdapter implements UserRoleRepository {

	private final UserRoleJpaRepository jpaRepository;
	private final UsersGymRoleMapper mapper;

	public UserRoleRepositoryAdapter(UserRoleJpaRepository jpaRepository, UsersGymRoleMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public boolean existsByUserGymIdAndRoleId(Long userGymId, Long roleId) {
		return jpaRepository.existsByUserGymIdAndRoleId(userGymId, roleId);
	}

	@Override
	public void deleteByUserGymId(Long userGymId) {
		jpaRepository.deleteByUserGymId(userGymId);
	}

	@Override
	public List<Long> findRoleIdsByUserGymId(Long userGymId) {
		return jpaRepository.findRoleIdsByUserGymId(userGymId);
	}

	@Override
	public List<Long> findRoleIdsByUserId(Long userId) {
		return jpaRepository.findRoleIdsByUserId(userId);
	}

	@Override
	public UsersGymRole save(UsersGymRole entity) {
		UsersGymRoleJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(entity));
		return mapper.toDomain(saved);
	}
}
