package com.conovax.fitflow.notifications.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailTemplate {
	private Long id;
	private String code;
	private String subject;
	private String html;
	private Boolean enabled;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
