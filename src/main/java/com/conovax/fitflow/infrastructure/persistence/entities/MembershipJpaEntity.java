package com.conovax.sexbody.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "memberships")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MembershipJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "membership_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, length = 80)
	private String name;

	@Column(nullable = false, length = 200)
	private String description;

	@Column(name = "duration_day", nullable = false)
	private Integer durationDay;

	@Column(nullable = false)
	private Boolean status;

	@Column(name = "gym_id")
	private Long gymId;
}
