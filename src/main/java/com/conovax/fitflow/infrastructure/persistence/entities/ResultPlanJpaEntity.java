package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "result_plan")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ResultPlanJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "result_plan_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "training_plan_id", nullable = false)
	private Long trainingPlanId;

	@Column(name = "start_weight", nullable = false, precision = 6, scale = 0)
	private BigDecimal startWeight;

	@Column(name = "end_weight", nullable = false, precision = 6, scale = 0)
	private BigDecimal endWeight;

	@Column(name = "start_fat", nullable = false, precision = 3, scale = 0)
	private BigDecimal startFat;

	@Column(name = "end_fat", nullable = false, precision = 3, scale = 0)
	private BigDecimal endFat;

	@Column(name = "start_muscle_mass", nullable = false, precision = 3, scale = 0)
	private BigDecimal startMuscleMass;

	@Column(name = "end_muscle_mass", nullable = false, precision = 3, scale = 0)
	private BigDecimal endMuscleMass;

	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;

	@Column
	private Boolean status;
}
