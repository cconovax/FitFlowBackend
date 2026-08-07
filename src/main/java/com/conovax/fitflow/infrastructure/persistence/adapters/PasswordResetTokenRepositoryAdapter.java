package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.PasswordResetToken;
import com.conovax.fitflow.domain.repositories.PasswordResetTokenRepository;
import com.conovax.fitflow.infrastructure.persistence.mappers.PasswordResetTokenMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.PasswordResetTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PasswordResetTokenRepositoryAdapter implements PasswordResetTokenRepository {

	private final PasswordResetTokenJpaRepository jpaRepository;
	private final PasswordResetTokenMapper mapper;

	@Override
	public PasswordResetToken save(PasswordResetToken token) {
		return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(token)));
	}

	@Override
	public Optional<PasswordResetToken> findByToken(String token) {
		return jpaRepository.findByToken(token).map(mapper::toDomain);
	}

	@Override
	public void deleteByUserId(Long userId) {
		jpaRepository.deleteByUserId(userId);
	}
}
