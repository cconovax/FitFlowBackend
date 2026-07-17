package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "user_gym_id", nullable = false)
	private Long userGymId;

	@Column(name = "payment_method_id", nullable = false)
	private Long paymentMethodId;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "payment_date", nullable = false)
	private LocalDate paymentDate;

	@Column(name = "payment_status_id", nullable = false)
	private Long paymentStatusId;

	@Column(name = "payment_reference", length = 30)
	private String paymentReference;

	@Column(nullable = false)
	private Boolean status;
}
