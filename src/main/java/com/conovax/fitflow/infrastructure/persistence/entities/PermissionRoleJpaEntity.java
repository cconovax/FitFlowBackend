package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permission_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PermissionRoleJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "permission_role_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "permission_id", nullable = false)
	private Long permissionId;

	@Column(name = "role_id", nullable = false)
	private Long roleId;
}
