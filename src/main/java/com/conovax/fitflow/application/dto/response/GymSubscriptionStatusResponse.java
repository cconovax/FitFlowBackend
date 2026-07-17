package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Estado actual de la suscripción SaaS de un gym")
public record GymSubscriptionStatusResponse(
		@Schema(description = "ID de la orden interna", example = "5")
		Long orderId,

		@Schema(description = "Estado de la orden: PENDING, ACTIVE, PAST_DUE, CANCELLED", example = "ACTIVE")
		String status,

		@Schema(description = "ID del plan SaaS contratado", example = "2")
		Long saasPlanId,

		@Schema(description = "Nombre del plan SaaS", example = "Plan Pro")
		String planName,

		@Schema(description = "Descripción del plan", example = "Acceso completo")
		String planDescription,

		@Schema(description = "Precio del plan", example = "149900.00")
		java.math.BigDecimal planPrice,

		@Schema(description = "Ciclo de facturación en días", example = "30")
		Long numDays,

		@Schema(description = "ID de suscripción en la pasarela de pago", example = "sub_1OxxxxxYYYYYY")
		String stripeSubscriptionId,

		@Schema(description = "Pasarela de pago utilizada", example = "STRIPE")
		String paymentGateway,

		@Schema(description = "Tipo de método de pago usado", example = "CARD")
		String paymentMethodType,

		@Schema(description = "Correo del cliente", example = "gym@example.com")
		String customerEmail,

		@Schema(description = "Fecha de creación de la orden")
		java.time.LocalDateTime createdAt,

		@Schema(description = "Features incluidas en el plan contratado")
		List<SaasPlanFeatureResponse> planFeatures
) {
}
