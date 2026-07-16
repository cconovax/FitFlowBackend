package com.conovax.sexbody.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GymPaymentOrder {
	private Long id;
	private Long gymId;
	private Long saasPlanId;
	private String stripeSubscriptionId;
	private String stripeCustomerId;
	private String paymentGateway;    // STRIPE (futuro: PSE_GATEWAY, NEQUI_GATEWAY, etc.)
	private String paymentMethodType; // CARD (futuro: PSE, NEQUI, etc.)
	private Long amountInCents;
	private String currency;
	private String status;
	private String customerEmail;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
