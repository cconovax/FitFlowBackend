package com.conovax.sexbody.infrastructure.payment;

import com.conovax.sexbody.domain.ports.PaymentGatewayPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Registro central de pasarelas de pago.
 *
 * Spring inyecta automáticamente todas las implementaciones de {@link PaymentGatewayPort}.
 * Para registrar una nueva pasarela (PSE, Nequi, etc.) basta con:
 *   1. Crear la clase que implementa PaymentGatewayPort.
 *   2. Anotarla con @Component.
 *   3. Agregar la entrada en {@link #resolveGatewayCode(String)}.
 */
@Slf4j
@Component
public class PaymentGatewayRegistry {

	private final Map<String, PaymentGatewayPort> gateways;

	public PaymentGatewayRegistry(List<PaymentGatewayPort> gatewayList) {
		this.gateways = gatewayList.stream()
				.collect(Collectors.toMap(PaymentGatewayPort::getGatewayCode, Function.identity()));
		log.info("Pasarelas de pago registradas: {}", gateways.keySet());
	}

	/**
	 * Devuelve la pasarela adecuada para el método de pago solicitado.
	 *
	 * @param paymentMethodType tipo de pago (CARD, PSE, NEQUI…)
	 * @throws IllegalStateException si el método no está soportado
	 */
	public PaymentGatewayPort getGatewayForPaymentMethod(String paymentMethodType) {
		String code = resolveGatewayCode(paymentMethodType);
		PaymentGatewayPort gateway = gateways.get(code);
		if (gateway == null) {
			throw new IllegalStateException(
					"Pasarela '" + code + "' no está registrada. Verifica la configuración."
			);
		}
		return gateway;
	}

	/**
	 * Devuelve la pasarela por su código directo (usado al cancelar/consultar).
	 *
	 * @param gatewayCode código de la pasarela (ej: "STRIPE")
	 */
	public PaymentGatewayPort getByCode(String gatewayCode) {
		PaymentGatewayPort gateway = gateways.get(gatewayCode != null ? gatewayCode.toUpperCase() : "STRIPE");
		if (gateway == null) {
			throw new IllegalStateException("Pasarela de pago no encontrada: " + gatewayCode);
		}
		return gateway;
	}

	/**
	 * Mapea tipo de pago → código de pasarela.
	 * Aquí se extiende el soporte de nuevas pasarelas en el futuro.
	 */
	private String resolveGatewayCode(String paymentMethodType) {
		if (paymentMethodType == null || paymentMethodType.isBlank()) {
			return "STRIPE"; // default
		}
		return switch (paymentMethodType.toUpperCase()) {
			case "CARD" -> "STRIPE";
			// Futuros métodos:
			// case "PSE"   -> "KUSHKI";
			// case "NEQUI" -> "NEQUI_GATEWAY";
			default -> throw new IllegalStateException(
					"Método de pago '" + paymentMethodType + "' no está soportado. " +
					"Métodos disponibles: CARD"
			);
		};
	}
}
