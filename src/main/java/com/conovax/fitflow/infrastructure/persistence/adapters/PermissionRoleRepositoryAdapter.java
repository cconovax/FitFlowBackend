package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.PermissionRole;
import com.conovax.sexbody.domain.repositories.PermissionRoleRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.PermissionRoleJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.PermissionRoleMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.PermissionRoleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRoleRepositoryAdapter implements PermissionRoleRepository {

	private final PermissionRoleJpaRepository jpaRepository;
	private final PermissionRoleMapper mapper;

	public PermissionRoleRepositoryAdapter(PermissionRoleJpaRepository jpaRepository, PermissionRoleMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId) {
		return jpaRepository.existsByRoleIdAndPermissionId(roleId, permissionId);
	}

	@Override
	public PermissionRole save(PermissionRole permissionRole) {
		PermissionRoleJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(permissionRole));
		return mapper.toDomain(saved);
	}

	@Override
	public void deleteByRoleId(Long roleId) {
		jpaRepository.deleteByRoleId(roleId);
	}
}
