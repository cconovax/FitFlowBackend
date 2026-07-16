package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.PermissionRole;
import com.conovax.sexbody.infrastructure.persistence.entities.PermissionRoleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class PermissionRoleMapper {

	public PermissionRole toDomain(PermissionRoleJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return PermissionRole.builder()
				.id(jpaEntity.getId())
				.permissionId(jpaEntity.getPermissionId())
				.roleId(jpaEntity.getRoleId())
				.build();
	}

	public PermissionRoleJpaEntity toJpaEntity(PermissionRole domain) {
		if (domain == null) return null;

		return PermissionRoleJpaEntity.builder()
				.id(domain.getId())
				.permissionId(domain.getPermissionId())
				.roleId(domain.getRoleId())
				.build();
	}
}
