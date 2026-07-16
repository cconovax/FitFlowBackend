package com.conovax.sexbody.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "membership_benefits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MembershipBenefitJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "membership_benefit_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "membership_id", nullable = false)
	private Long membershipId;

	@Column(name = "benefit_id", nullable = false)
	private Long benefitId;
}
