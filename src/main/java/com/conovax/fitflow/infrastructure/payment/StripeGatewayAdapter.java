package com.conovax.fitflow.infrastructure.payment;

import com.conovax.fitflow.domain.ports.PaymentGatewayPort;
import com.conovax.fitflow.domain.ports.PaymentGatewayResult;
import com.conovax.fitflow.domain.ports.SubscriptionCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adaptador de Stripe como pasarela de pago.
 * Implementa {@link PaymentGatewayPort} delegando en {@link StripeService}.
 *
 * Para agregar una nueva pasarela (PSE, Nequi, etc.) basta con crear
 * otra clase que implemente PaymentGatewayPort y anotarla con @Component.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StripeGatewayAdapter implements PaymentGatewayPort {

	private final StripeService stripeService;

	@Override
	public String getGatewayCode() {
		return "STRIPE";
	}

	@Override
	public List<String> supportedPaymentMethods() {
		return List.of("CARD");
	}

	@Override
	public PaymentGatewayResult createSubscription(SubscriptionCommand command) {
		String customerId = stripeService.getOrCreateGymCustomer(
				command.gymId(), command.existingCustomerId(), command.customerEmail());
		stripeService.attachPaymentMethod(customerId, command.paymentToken());
		String subscriptionId = stripeService.createSubscription(
				customerId, command.planPriceRef(), command.paymentToken()
		);
		log.info("Suscripción Stripe creada: sub={} cus={} gym={}",
				subscriptionId, customerId, command.gymId());
		return new PaymentGatewayResult(subscriptionId, customerId, null, "PENDING");
	}

	@Override
	public void cancelSubscription(String gatewaySubscriptionId, boolean immediate) {
		stripeService.cancelSubscription(gatewaySubscriptionId, immediate);
	}
}
