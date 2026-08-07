package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository {
	PasswordResetToken save(PasswordResetToken token);
	Optional<PasswordResetToken> findByToken(String token);
	void deleteByUserId(Long userId);
}
