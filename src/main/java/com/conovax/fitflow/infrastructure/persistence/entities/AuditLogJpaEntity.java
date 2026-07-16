package com.conovax.sexbody.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AuditLogJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "audit_log_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "gym_id")
	private Long gymId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "user_gym_id")
	private Long userGymId;

	@Column(length = 80)
	private String username;

	@Column(nullable = false, length = 20)
	private String action;

	@Column(name = "http_method", nullable = false, length = 10)
	private String httpMethod;

	@Column(nullable = false, length = 300)
	private String path;

	@Column(name = "query_string", length = 500)
	private String queryString;

	@Column(columnDefinition = "text")
	private String payload;

	@Column(name = "ip_address", length = 45)
	private String ipAddress;

	@Column(name = "user_agent", length = 300)
	private String userAgent;

	@Column(name = "status_code")
	private Integer statusCode;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	void prePersist() {
		if (createdAt == null) {
			createdAt = LocalDateTime.now();
		}
	}
}
