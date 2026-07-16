package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.services.GymSaasSubscriptionService;
import com.conovax.sexbody.infrastructure.payment.StripeService;
import com.stripe.model.Event;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
@Tag(name = "Webhooks", description = "Endpoints públicos para eventos de pasarelas de pago")
public class StripeWebhookController {

	private final StripeService stripeService;
	private final GymSaasSubscriptionService subscriptionService;

	/**
	 * Recibe eventos de Stripe para gestión de suscripciones recurrentes.
	 * Este endpoint es público y NO requiere JWT.
	 * La seguridad se garantiza verificando la firma del webhook con el Stripe-Signature header.
	 *
	 * Configurar en el Dashboard de Stripe → Developers → Webhooks:
	 *   URL: https://tudominio.com/api/v1/webhooks/stripe
	 *   Eventos: customer.subscription.updated, customer.subscription.deleted
	 */
	@PostMapping("/stripe")
	@Operation(summary = "Webhook de eventos Stripe (público, verificación por firma)")
	public ResponseEntity<Map<String, String>> handleStripeEvent(
			@RequestBody String rawBody,
			@RequestHeader("Stripe-Signature") String sigHeader
	) {
		Event event;
		try {
			event = stripeService.constructWebhookEvent(rawBody, sigHeader);
		} catch (SecurityException ex) {
			log.warn("Firma inválida en webhook Stripe: {}", ex.getMessage());
			return ResponseEntity.status(401).body(Map.of("error", "Firma inválida"));
		}

		log.info("Webhook Stripe recibido: type={}, id={}", event.getType(), event.getId());

		try {
			subscriptionService.handleStripeEvent(event);
		} catch (Exception ex) {
			// Siempre retornar 200 para evitar que Stripe reenvíe el evento indefinidamente.
			// Los errores internos se registran en los logs.
			log.error("Error interno procesando webhook Stripe {} ({}): {}",
					event.getType(), event.getId(), ex.getMessage(), ex);
		}

		return ResponseEntity.ok(Map.of("received", "true"));
	}
}
