package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resultado del inicio de suscripción de un gym a un plan SaaS")
public record GymSubscribeResponse(
		@Schema(description = "ID de la orden de pago interna", example = "5")
		Long orderId,

		@Schema(description = "ID de la suscripción en la pasarela de pago", example = "sub_1OxxxxxYYYYYY")
		String subscriptionId,

		@Schema(description = "URL de redirección para flujos fuera de sesión (null para CARD)", example = "null")
		String redirectUrl,

		@Schema(description = "Estado actual de la orden: PENDING, ACTIVE, FAILED, CANCELLED", example = "PENDING")
		String status,

		@Schema(description = "Mensaje descriptivo del resultado")
		String message
) {
}
