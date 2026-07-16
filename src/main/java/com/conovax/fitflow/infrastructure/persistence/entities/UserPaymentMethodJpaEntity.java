package com.conovax.sexbody.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_payment_methods")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserPaymentMethodJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_payment_method_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "payment_gateway_id", nullable = false)
	private Long paymentGatewayId;

	@Column(nullable = false)
	private String token;

	@Column(name = "masked_pan", nullable = false, length = 4)
	private String maskedPan;

	@Column(name = "expired_date", nullable = false, length = 6)
	private String expiredDate;

	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;

	@Column
	private Boolean status;
}
