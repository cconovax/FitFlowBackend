package com.conovax.sexbody.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, unique = true, length = 60)
	private String name;

	@Column(nullable = false, length = 60)
	private String code;

	@Column(name = "gym_id")
	private Long gymId;

	@Column(name = "full_access", nullable = false)
	@Builder.Default
	private Boolean fullAccess = false;

	@Column(name = "is_staff", nullable = false)
	@Builder.Default
	private Boolean isStaff = false;

	@Column(nullable = false)
	@Builder.Default
	private Boolean status = true;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "permission_role",
			joinColumns = @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "permission_id")
	)
	@Builder.Default
	private Set<PermissionJpaEntity> permissions = new HashSet<>();
}
