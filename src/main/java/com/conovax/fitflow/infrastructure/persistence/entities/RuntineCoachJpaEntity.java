package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "runtine_coachs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RuntineCoachJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "runtine_coach_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "runtine_id", nullable = false)
	private Long runtineId;

	@Column(name = "coach_id", nullable = false)
	private Long coachId;

	@Column
	private Boolean status;
}
