package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.AddPaymentMethodRequest;
import com.conovax.sexbody.application.dto.request.GymSubscribeRequest;
import com.conovax.sexbody.application.dto.response.GymSubscribeResponse;
import com.conovax.sexbody.application.dto.response.GymSubscriptionStatusResponse;
import com.conovax.sexbody.application.dto.response.PaymentMethodResponse;
import com.conovax.sexbody.application.dto.response.SaasPlanResponse;
import com.conovax.sexbody.application.services.GymSaasSubscriptionService;
import com.conovax.sexbody.domain.entities.GymPaymentOrder;
import com.conovax.sexbody.infrastructure.security.annotations.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Gym SaaS Subscription", description = "Flujo self-service de suscripción de gyms a planes SaaS")
@SecurityRequirement(name = "bearerAuth")
public class GymSaasSubscriptionController {

	private final GymSaasSubscriptionService service;

	/**
	 * Lista los planes SaaS disponibles para que el gym explore antes de suscribirse.
	 */
	@GetMapping("/api/v1/saas/plans/available")
	@Operation(summary = "Listar planes SaaS disponibles para suscripción")
	public ResponseEntity<List<SaasPlanResponse>> getAvailablePlans() {
		return ResponseEntity.ok(service.getAvailablePlans());
	}

	/**
	 * Inicia la suscripción del gym al plan SaaS seleccionado con pago recurrente vía Stripe.
	 *
	 * El frontend debe:
	 * 1. Usar Stripe.js / Stripe Elements para capturar la tarjeta.
	 * 2. Llamar a stripe.createPaymentMethod() para obtener el paymentMethodId (pm_xxx).
	 * 3. Enviar el paymentMethodId + customerEmail + saasPlanId a este endpoint.
	 */
	@PostMapping("/api/v1/gyms/{gymId}/saas/subscribe")
	@Operation(summary = "Suscribir gym a un plan SaaS con pago recurrente vía Stripe")
	@RequirePermission("subscribe:gym:saas")
	public ResponseEntity<GymSubscribeResponse> subscribe(
			@PathVariable Long gymId,
			@Valid @RequestBody GymSubscribeRequest request
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.subscribe(gymId, request));
	}

	/**
	 * Consulta el estado actual de la suscripción del gym (última orden, cualquier estado).
	 * Devuelve null (204) si el gym nunca se ha suscrito.
	 */
	@GetMapping("/api/v1/gyms/{gymId}/saas/subscription")
	@Operation(summary = "Consultar estado actual de la suscripción SaaS del gym")
	@RequirePermission("get:status:subscription:gym")
	public ResponseEntity<GymSubscriptionStatusResponse> getCurrentSubscription(@PathVariable Long gymId) {
		GymSubscriptionStatusResponse status = service.getCurrentSubscription(gymId);
		return status != null ? ResponseEntity.ok(status) : ResponseEntity.noContent().build();
	}

	/**
	 * Cancela la suscripción activa del gym.
	 *
	 * @param immediate true  = cancela ahora mismo (sin reembolso)
	 *                  false = cancela al final del período pagado (default)
	 */
	@DeleteMapping("/api/v1/gyms/{gymId}/saas/subscription")
	@Operation(summary = "Cancelar la suscripción SaaS del gym")
	@RequirePermission("cancel:subscription:saas:gym")
	public ResponseEntity<Void> cancelSubscription(
			@PathVariable Long gymId,
			@RequestParam(defaultValue = "false") boolean immediate
	) {
		service.cancelSubscription(gymId, immediate);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Consulta el estado de una orden de pago específica del gym.
	 */
	@GetMapping("/api/v1/gyms/{gymId}/saas/orders/{orderId}")
	@Operation(summary = "Consultar estado de una orden de pago SaaS")
	@RequirePermission("get:status:subscription:gym")
	public ResponseEntity<GymPaymentOrder> getOrder(
			@PathVariable Long gymId,
			@PathVariable Long orderId
	) {
		return ResponseEntity.ok(service.getOrderById(gymId, orderId));
	}

	// -------------------------------------------------------------------------
	// Métodos de pago
	// -------------------------------------------------------------------------

	/**
	 * Lista las tarjetas registradas en Stripe para el gym.
	 * El gym debe tener una suscripción activa.
	 */
	@GetMapping("/api/v1/gyms/{gymId}/payment-methods")
	@Operation(summary = "Listar métodos de pago del gym en Stripe")
	@RequirePermission("get:payment:method:gym")
	public ResponseEntity<List<PaymentMethodResponse>> listPaymentMethods(
			@PathVariable Long gymId
	) {
		return ResponseEntity.ok(service.listPaymentMethods(gymId));
	}

	/**
	 * Agrega una nueva tarjeta y la establece como predeterminada para los cobros automáticos.
	 *
	 * El frontend debe obtener el paymentMethodId con Stripe.js (stripe.createPaymentMethod()).
	 */
	@PostMapping("/api/v1/gyms/{gymId}/payment-methods")
	@Operation(summary = "Agregar método de pago y establecer como predeterminado")
	@RequirePermission("create:payment:method:gym")
	public ResponseEntity<List<PaymentMethodResponse>> addPaymentMethod(
			@PathVariable Long gymId,
			@Valid @RequestBody AddPaymentMethodRequest request
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.addPaymentMethod(gymId, request));
	}

	/**
	 * Establece una tarjeta existente como predeterminada para los cobros automáticos.
	 */
	@PutMapping("/api/v1/gyms/{gymId}/payment-methods/{paymentMethodId}/default")
	@Operation(summary = "Establecer método de pago predeterminado")
	@RequirePermission("default:payment:method:gym")
	public ResponseEntity<List<PaymentMethodResponse>> setDefaultPaymentMethod(
			@PathVariable Long gymId,
			@PathVariable String paymentMethodId
	) {
		return ResponseEntity.ok(service.setDefaultPaymentMethod(gymId, paymentMethodId));
	}

	/**
	 * Elimina una tarjeta del gym. No se puede eliminar la tarjeta predeterminada activa.
	 */
	@DeleteMapping("/api/v1/gyms/{gymId}/payment-methods/{paymentMethodId}")
	@Operation(summary = "Eliminar método de pago del gym")
	@RequirePermission("delete:payment:method:gym")
	public ResponseEntity<Void> removePaymentMethod(
			@PathVariable Long gymId,
			@PathVariable String paymentMethodId
	) {
		service.removePaymentMethod(gymId, paymentMethodId);
		return ResponseEntity.noContent().build();
	}

}
