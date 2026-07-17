package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Método de pago registrado en Stripe")
public record PaymentMethodResponse(

		@Schema(description = "ID del método de pago en Stripe", example = "pm_1OxxxxxYYYYYYYY")
		String id,

		@Schema(description = "Marca de la tarjeta", example = "visa")
		String brand,

		@Schema(description = "Últimos 4 dígitos de la tarjeta", example = "4242")
		String last4,

		@Schema(description = "Mes de vencimiento", example = "12")
		Long expMonth,

		@Schema(description = "Año de vencimiento", example = "2027")
		Long expYear,

		@Schema(description = "Indica si es el método de pago predeterminado para cobros automáticos")
		boolean isDefault
) {}
