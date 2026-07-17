package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "countries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CountryJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "country_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, length = 60)
	private String name;

	@Column(length = 10)
	private String code;

	@Column(nullable = false)
	private Boolean status;
}
