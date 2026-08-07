package com.conovax.fitflow.application.services;

import com.conovax.fitflow.domain.entities.PasswordResetToken;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.PasswordResetTokenRepository;
import com.conovax.fitflow.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetService {

	private final UserRepository userRepository;
	private final PasswordResetTokenRepository tokenRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;

	@Value("${app.reset-password.base-url}")
	private String appBaseUrl;

	@Value("${app.reset-password.expiration-minutes:60}")
	private int expirationMinutes;

	/**
	 * Genera un token y envía el email de restablecimiento.
	 * Siempre devuelve éxito para no revelar si el email existe.
	 */
	@Transactional
	public void requestReset(String email) {
		userRepository.findByEmail(email).ifPresent(user -> {
			tokenRepository.deleteByUserId(user.getId());

			String token = UUID.randomUUID().toString();
			tokenRepository.save(PasswordResetToken.builder()
					.userId(user.getId())
					.token(token)
					.expiresAt(LocalDateTime.now().plusMinutes(expirationMinutes))
					.used(false)
					.build());

			String resetUrl = appBaseUrl + "/reset-password?token=" + token;
			String userName = user.getPeople() != null ? user.getPeople().getNames() : email;

			emailService.sendEmail(email, "password_reset", Map.of(
					"userName", userName,
					"resetUrl", resetUrl,
					"expirationMinutes", String.valueOf(expirationMinutes)
			));

			log.info("Password restablecido por el usuario con ID: {}", user.getId());
		});
	}

	/** Valida que el token exista, no esté usado y no haya expirado. */
	@Transactional(readOnly = true)
	public void validateToken(String token) {
		PasswordResetToken resetToken = tokenRepository.findByToken(token)
				.orElseThrow(() -> new ResourceNotFoundException("Token inválido o expirado"));
		if (resetToken.isUsed() || resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("El enlace de restablecimiento ha expirado o ya fue utilizado");
		}
	}

	/** Restablece la contraseña y marca el token como usado. */
	@Transactional
	public void resetPassword(String token, String newPassword) {
		PasswordResetToken resetToken = tokenRepository.findByToken(token)
				.orElseThrow(() -> new ResourceNotFoundException("Token inválido o expirado"));

		if (resetToken.isUsed()) {
			throw new IllegalArgumentException("Este enlace ya fue utilizado");
		}
		if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("El enlace de restablecimiento ha expirado");
		}

		userRepository.updatePassword(resetToken.getUserId(), passwordEncoder.encode(newPassword));
		tokenRepository.save(resetToken.toBuilder().used(true).build());

		log.info("Password restablecido exitosamente por el usuario con ID {}", resetToken.getUserId());
	}
}
