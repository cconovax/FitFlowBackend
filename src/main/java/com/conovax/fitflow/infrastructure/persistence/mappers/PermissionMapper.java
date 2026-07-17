package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.Permission;
import com.conovax.fitflow.infrastructure.persistence.entities.PermissionJpaEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PermissionMapper {

	public Permission toDomain(PermissionJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return Permission.builder()
				.id(jpaEntity.getId())
				.name(jpaEntity.getSlug())
				.description(jpaEntity.getDescription())
				.basic(jpaEntity.getBasic())
				.status(jpaEntity.getStatus())
				.createdAt(null)
				.build();
	}

	public PermissionJpaEntity toJpaEntity(Permission domain) {
		if (domain == null) return null;

		return PermissionJpaEntity.builder()
				.id(domain.getId())
				.slug(domain.getName())
				.description(domain.getDescription())
				.basic(domain.getBasic())
				.status(domain.getStatus())
				.roles(new HashSet<>())
				.build();
	}

	public Set<Permission> toDomainSet(Set<PermissionJpaEntity> jpaEntities) {
		if (jpaEntities == null) return new HashSet<>();
		return jpaEntities.stream()
				.map(this::toDomain)
				.collect(Collectors.toSet());
	}

	public Set<PermissionJpaEntity> toJpaEntitySet(Set<Permission> domainEntities) {
		if (domainEntities == null) return new HashSet<>();
		return domainEntities.stream()
				.map(this::toJpaEntity)
				.collect(Collectors.toSet());
	}
}
