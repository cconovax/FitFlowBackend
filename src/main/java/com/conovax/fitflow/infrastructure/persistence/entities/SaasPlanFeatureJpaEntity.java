package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "saas_plan_features")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaasPlanFeatureJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "saas_plan_feature_id")
	@EqualsAndHashCode.Include
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "saas_plan_id", nullable = false)
	private SaasPlanJpaEntity saasPlan;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "saas_feature_id", nullable = false)
	private SaasFeatureJpaEntity saasFeature;

	@Column(name = "value_text", length = 100)
	private String valueText;

	@Column(name = "value_number", precision = 12, scale = 2)
	private BigDecimal valueNumber;

	@Column(name = "value_boolean")
	private Boolean valueBoolean;
}