package com.conovax.sexbody.domain.ports;

/**
 * Resultado de la creación de una suscripción en una pasarela de pago.
 *
 * @param subscriptionId ID de la suscripción en la pasarela (sub_xxx en Stripe)
 * @param customerId     ID del cliente en la pasarela (cus_xxx en Stripe; puede ser null)
 * @param redirectUrl    URL de redirección para flujos fuera de sesión (PSE, Nequi); null para CARD
 * @param status         Estado inicial: PENDING (requiere confirmación) o ACTIVE
 */
public record PaymentGatewayResult(
		String subscriptionId,
		String customerId,
		String redirectUrl,
		String status
) {}
