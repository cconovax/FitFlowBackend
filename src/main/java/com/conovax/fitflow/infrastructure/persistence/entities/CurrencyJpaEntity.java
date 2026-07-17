package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "currencies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CurrencyJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "currency_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false, length = 10)
	private String code;

	@Column
	private Boolean status;
}
