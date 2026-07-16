package com.conovax.sexbody.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PermissionJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "permission_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "slug", nullable = false, unique = true, length = 70)
	private String slug;

	@Column(nullable = false, length = 200)
	private String description;

	@Column(nullable = false)
	@Builder.Default
	private Boolean basic = true;

	@Column(nullable = false)
	@Builder.Default
	private Boolean status = true;

	@ManyToMany(mappedBy = "permissions")
	@Builder.Default
	private Set<RoleJpaEntity> roles = new HashSet<>();
}
