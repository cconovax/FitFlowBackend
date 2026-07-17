package com.conovax.fitflow.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditLog {
	private Long id;
	private Long gymId;
	private Long userId;
	private Long userGymId;
	private String username;
	private String action;
	private String httpMethod;
	private String path;
	private String queryString;
	private String payload;
	private String ipAddress;
	private String userAgent;
	private Integer statusCode;
	private LocalDateTime createdAt;
}
