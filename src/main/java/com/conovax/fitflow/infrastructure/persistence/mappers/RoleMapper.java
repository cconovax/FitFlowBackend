package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.Role;
import com.conovax.fitflow.infrastructure.persistence.entities.RoleJpaEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

	private final PermissionMapper permissionMapper;

	public RoleMapper(PermissionMapper permissionMapper) {
		this.permissionMapper = permissionMapper;
	}

	public Role toDomain(RoleJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return Role.builder()
				.id(jpaEntity.getId())
				.name(jpaEntity.getName())
				.code(jpaEntity.getCode())
				.gymId(jpaEntity.getGymId())
				.fullAccess(jpaEntity.getFullAccess())
				.isStaff(jpaEntity.getIsStaff())
				.status(jpaEntity.getStatus())
				.description(null)
				.createdAt(null)
				.permissions(permissionMapper.toDomainSet(jpaEntity.getPermissions()))
				.build();
	}

	public RoleJpaEntity toJpaEntity(Role domain) {
		if (domain == null) return null;

		String code = domain.getCode();
		if (code == null || code.isBlank()) {
			code = domain.getName();
		}

		return RoleJpaEntity.builder()
				.id(domain.getId())
				.name(domain.getName())
				.code(code)
				.gymId(domain.getGymId())
				.fullAccess(domain.getFullAccess() != null ? domain.getFullAccess() : false)
				.isStaff(domain.getIsStaff() != null ? domain.getIsStaff() : false)
				.status(domain.getStatus() != null ? domain.getStatus() : true)
				.permissions(permissionMapper.toJpaEntitySet(domain.getPermissions()))
				.build();
	}

	public Set<Role> toDomainSet(Set<RoleJpaEntity> jpaEntities) {
		if (jpaEntities == null) return new HashSet<>();
		return jpaEntities.stream()
				.map(this::toDomain)
				.collect(Collectors.toSet());
	}

	public Set<RoleJpaEntity> toJpaEntitySet(Set<Role> domainEntities) {
		if (domainEntities == null) return new HashSet<>();
		return domainEntities.stream()
				.map(this::toJpaEntity)
				.collect(Collectors.toSet());
	}
}
