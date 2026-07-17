package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_gyms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsersGymJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_gym_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "gym_id", nullable = false)
	private Long gymId;

	@Column(name = "fingerprint", nullable = true, columnDefinition = "bytea")
	private byte[] fingerprint;

	@Column(nullable = false)
	@Builder.Default
	private Boolean status = true;
}
