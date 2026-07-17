package com.conovax.fitflow.domain.ports;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

/**
 * Comando para crear una suscripción, agnóstico a la pasarela de pago.
 *
 * @param gymId           ID del gym suscriptor
 * @param saasPlanId      ID del plan SaaS
 * @param planPriceRef    Referencia del precio en la pasarela (ej: price_xxx en Stripe)
 * @param paymentMethodType  Tipo de pago: CARD, PSE, NEQUI, etc.
 * @param customerEmail   Email del cliente
 * @param existingCustomerId customerId previo del gym en la pasarela (cus_xxx) o null si no tiene
 * @param paymentToken    Token del método de pago (pm_xxx para CARD/Stripe; null en flujos redirect)
 * @param amount          Monto del cobro
 * @param currency        Moneda (COP, USD, etc.)
 * @param extraParams     Parámetros adicionales específicos de la pasarela (bankCode para PSE, etc.)
 */
public record SubscriptionCommand(
		Long gymId,
		Long saasPlanId,
		String planPriceRef,
		String paymentMethodType,
		String customerEmail,
		String existingCustomerId,
		String paymentToken,
		BigDecimal amount,
		String currency,
		Map<String, String> extraParams
) {
	/** Crea un comando sin parámetros extra. */
	public static SubscriptionCommand of(
			Long gymId, Long saasPlanId, String planPriceRef,
			String paymentMethodType, String customerEmail, String existingCustomerId,
			String paymentToken, BigDecimal amount, String currency) {
		return new SubscriptionCommand(
				gymId, saasPlanId, planPriceRef,
				paymentMethodType, customerEmail, existingCustomerId,
				paymentToken, amount, currency,
				Collections.emptyMap()
		);
	}
}
