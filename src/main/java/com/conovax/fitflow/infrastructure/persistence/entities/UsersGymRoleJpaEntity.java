package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_gym_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsersGymRoleJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_gym_role_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "user_gym_id", nullable = false)
	private Long userGymId;

	@Column(name = "role_id", nullable = false)
	private Long roleId;
}
