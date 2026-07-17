package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.UsersGymRole;
import com.conovax.fitflow.infrastructure.persistence.entities.UsersGymRoleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UsersGymRoleMapper {

	public UsersGymRole toDomain(UsersGymRoleJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return UsersGymRole.builder()
				.id(jpaEntity.getId())
				.userGymId(jpaEntity.getUserGymId())
				.roleId(jpaEntity.getRoleId())
				.build();
	}

	public UsersGymRoleJpaEntity toJpaEntity(UsersGymRole domain) {
		if (domain == null) return null;

		return UsersGymRoleJpaEntity.builder()
				.id(domain.getId())
				.userGymId(domain.getUserGymId())
				.roleId(domain.getRoleId())
				.build();
	}
}
