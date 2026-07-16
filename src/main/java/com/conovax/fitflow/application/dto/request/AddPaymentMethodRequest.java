package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud para agregar o establecer un método de pago")
public record AddPaymentMethodRequest(

		@NotBlank(message = "El Payment Method ID es requerido")
		@Schema(
				description = "ID del método de pago obtenido desde Stripe.js (stripe.createPaymentMethod())",
				example = "pm_1OxxxxxYYYYYYYY"
		)
		String paymentMethodId
) {}
