package com.conovax.sexbody.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rule_access")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RuleAccessJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rule_access_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "role_id", nullable = false)
	private Long roleId;

	@Column(name = "required_membership", nullable = false)
	@Builder.Default
	private Boolean requiredMembership = true;
}
