package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_methods")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentMethodJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_method_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false)
	private Boolean status;
}
