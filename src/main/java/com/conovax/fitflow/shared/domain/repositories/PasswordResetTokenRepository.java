package com.conovax.fitflow.shared.domain.repositories;

import com.conovax.fitflow.shared.domain.entities.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository {
	PasswordResetToken save(PasswordResetToken token);
	Optional<PasswordResetToken> findByToken(String token);
	void deleteByUserId(Long userId);
}
