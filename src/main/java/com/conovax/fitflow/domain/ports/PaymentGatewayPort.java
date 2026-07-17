package com.conovax.fitflow.domain.ports;

import java.util.List;

/**
 * Puerto de salida (hexagonal) para integración con pasarelas de pago.
 * Cada proveedor (Stripe, PSE, Nequi, etc.) implementa esta interfaz.
 */
public interface PaymentGatewayPort {

	/** Código único de la pasarela. Ej: "STRIPE". */
	String getGatewayCode();

	/** Tipos de pago soportados por esta pasarela. Ej: ["CARD"]. */
	List<String> supportedPaymentMethods();

	/**
	 * Crea una suscripción recurrente en la pasarela.
	 *
	 * @param command datos de la suscripción, agnósticos a la pasarela
	 * @return resultado con IDs y, opcionalmente, URL de redirección
	 */
	PaymentGatewayResult createSubscription(SubscriptionCommand command);

	/**
	 * Cancela una suscripción activa.
	 *
	 * @param gatewaySubscriptionId ID de la suscripción en la pasarela
	 * @param immediate true = cancela ahora, false = al final del período
	 */
	void cancelSubscription(String gatewaySubscriptionId, boolean immediate);
}
