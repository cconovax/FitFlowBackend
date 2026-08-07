package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.PasswordResetToken;
import com.conovax.fitflow.infrastructure.persistence.entities.PasswordResetTokenJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetTokenMapper {

	public PasswordResetToken toDomain(PasswordResetTokenJpaEntity e) {
		if (e == null) return null;
		return PasswordResetToken.builder()
				.id(e.getId())
				.userId(e.getUserId())
				.token(e.getToken())
				.expiresAt(e.getExpiresAt())
				.used(e.isUsed())
				.createdAt(e.getCreatedAt())
				.build();
	}

	public PasswordResetTokenJpaEntity toJpaEntity(PasswordResetToken d) {
		if (d == null) return null;
		return PasswordResetTokenJpaEntity.builder()
				.id(d.getId())
				.userId(d.getUserId())
				.token(d.getToken())
				.expiresAt(d.getExpiresAt())
				.used(d.isUsed())
				.build();
	}
}
