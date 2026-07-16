package com.conovax.sexbody.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gym_payment_methods")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GymPaymentMethodJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gym_payment_method_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "gym_id", nullable = false)
	private Long gymId;

	@Column(name = "payment_method_id", nullable = false)
	private Long paymentMethodId;

	@Column(nullable = false)
	private Boolean status;

	@Column(nullable = false)
	private Boolean online;
}
