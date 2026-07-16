package com.conovax.sexbody.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "membership_coachs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MembershipCoachJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "membership_coach_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "membership_id", nullable = false)
	private Long membershipId;

	@Column(name = "coach_id", nullable = false)
	private Long coachId;

	@Column
	private Boolean status;
}
