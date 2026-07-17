package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "municipalities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MunicipalityJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "municipalitie_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "departament_id", nullable = false)
	private Long departamentId;

	@Column(nullable = false, length = 60)
	private String name;

	@Column(nullable = false)
	private Boolean status;
}
