package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "saas_features")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaasFeatureJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "saas_feature_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, length = 60, unique = true)
	private String code;

	@Column(nullable = false, length = 80)
	private String name;

	@Column(length = 200)
	private String description;

	@Column(name = "feature_type", nullable = false, length = 30)
	private String featureType;

	@Column(nullable = false)
	private Boolean status;
}