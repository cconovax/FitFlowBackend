package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "saas_plans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaasPlanJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "saas_plan_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, length = 40, unique = true)
	private String code;

	@Column(nullable = false, length = 80)
	private String name;

    @Column(nullable = false, length = 80)
    private String billing_cycle;

	@Column(length = 200)
	private String description;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal price;

	@Column(name = "num_days", nullable = false, length = 1000)
	private Long numDays;

	@Column(name = "stripe_price_id", length = 100)
	private String stripePriceId;

	@Column(nullable = false)
	private Boolean status;
}