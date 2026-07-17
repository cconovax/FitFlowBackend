package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "departaments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DepartamentJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "departament_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "contry_id", nullable = false)
	private Long contryId;

	@Column(nullable = false, length = 60)
	private String name;

	@Column(nullable = false)
	private Boolean status;
}
