package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_gateways")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentGatewayJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_gateway_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false)
	private Boolean status;
}
