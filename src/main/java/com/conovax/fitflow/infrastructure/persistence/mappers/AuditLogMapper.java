package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.AuditLog;
import com.conovax.sexbody.infrastructure.persistence.entities.AuditLogJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

	public AuditLog toDomain(AuditLogJpaEntity jpaEntity) {
		if (jpaEntity == null) {
			return null;
		}
		return AuditLog.builder()
				.id(jpaEntity.getId())
				.gymId(jpaEntity.getGymId())
				.userId(jpaEntity.getUserId())
				.userGymId(jpaEntity.getUserGymId())
				.username(jpaEntity.getUsername())
				.action(jpaEntity.getAction())
				.httpMethod(jpaEntity.getHttpMethod())
				.path(jpaEntity.getPath())
				.queryString(jpaEntity.getQueryString())
				.payload(jpaEntity.getPayload())
				.ipAddress(jpaEntity.getIpAddress())
				.userAgent(jpaEntity.getUserAgent())
				.statusCode(jpaEntity.getStatusCode())
				.createdAt(jpaEntity.getCreatedAt())
				.build();
	}

	public AuditLogJpaEntity toJpaEntity(AuditLog domain) {
		if (domain == null) {
			return null;
		}
		return AuditLogJpaEntity.builder()
				.id(domain.getId())
				.gymId(domain.getGymId())
				.userId(domain.getUserId())
				.userGymId(domain.getUserGymId())
				.username(domain.getUsername())
				.action(domain.getAction())
				.httpMethod(domain.getHttpMethod())
				.path(domain.getPath())
				.queryString(domain.getQueryString())
				.payload(domain.getPayload())
				.ipAddress(domain.getIpAddress())
				.userAgent(domain.getUserAgent())
				.statusCode(domain.getStatusCode())
				.createdAt(domain.getCreatedAt())
				.build();
	}
}
