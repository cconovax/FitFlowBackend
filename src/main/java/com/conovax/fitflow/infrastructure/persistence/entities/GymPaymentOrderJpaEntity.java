package com.conovax.sexbody.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "gym_payment_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GymPaymentOrderJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gym_payment_order_id")
	@EqualsAndHashCode.Include
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gym_id", nullable = false)
	private GymJpaEntity gym;

	@Column(name = "saas_plan_id", nullable = false)
	private Long saasPlanId;

	@Column(name = "stripe_subscription_id", length = 100)
	private String stripeSubscriptionId;

	@Column(name = "stripe_customer_id", length = 100)
	private String stripeCustomerId;

	@Column(name = "payment_gateway", nullable = false, length = 30)
	@Builder.Default
	private String paymentGateway = "STRIPE";

	@Column(name = "payment_method_type", nullable = false, length = 20)
	@Builder.Default
	private String paymentMethodType = "CARD";

	@Column(name = "amount_in_cents", nullable = false)
	private Long amountInCents;

	@Column(nullable = false, length = 10)
	@Builder.Default
	private String currency = "COP";

	@Column(nullable = false, length = 20)
	@Builder.Default
	private String status = "PENDING";

	@Column(name = "customer_email", nullable = false, length = 150)
	private String customerEmail;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void preUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
