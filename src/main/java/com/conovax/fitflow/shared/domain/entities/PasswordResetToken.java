package com.conovax.fitflow.shared.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordResetToken {
	private Long id;
	private Long userId;
	private String token;
	private LocalDateTime expiresAt;
	private boolean used;
	private LocalDateTime createdAt;
}
