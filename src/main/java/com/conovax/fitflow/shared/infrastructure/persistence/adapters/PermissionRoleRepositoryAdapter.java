package com.conovax.fitflow.shared.infrastructure.persistence.adapters;

import com.conovax.fitflow.shared.domain.entities.PermissionRole;
import com.conovax.fitflow.shared.domain.repositories.PermissionRoleRepository;
import com.conovax.fitflow.shared.infrastructure.persistence.entities.PermissionRoleJpaEntity;
import com.conovax.fitflow.shared.infrastructure.persistence.mappers.PermissionRoleMapper;
import com.conovax.fitflow.shared.infrastructure.persistence.repositories.PermissionRoleJpaRepository;
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
