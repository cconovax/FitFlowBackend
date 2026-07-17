package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Solicitud para suscribir un gym a un plan SaaS con pago recurrente")
public record GymSubscribeRequest(
		@NotNull(message = "El ID del plan SaaS es requerido")
		@Schema(description = "ID del plan SaaS al que desea suscribirse", example = "1")
		Long saasPlanId,

		@NotBlank(message = "El método de pago es requerido")
		@Schema(description = "Tipo de método de pago: CARD (más métodos próximamente)", example = "CARD")
		String paymentMethodType,

		@NotBlank(message = "El Payment Method ID es requerido")
		@Schema(description = "Token del método de pago obtenido del SDK de la pasarela (pm_xxx para CARD/Stripe)", example = "pm_1OxxxxxYYYYYY")
		String paymentMethodId,

		@NotBlank(message = "El email es requerido")
		@Email(message = "El email debe tener un formato válido")
		@Schema(description = "Email del cliente para recibos", example = "pagos@migym.com")
		String customerEmail
) {
}
