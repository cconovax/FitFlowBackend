package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.AddPaymentMethodRequest;
import com.conovax.sexbody.application.dto.request.GymSubscribeRequest;
import com.conovax.sexbody.application.dto.request.GymSubscriptionRequest;
import com.conovax.sexbody.application.dto.response.GymSubscribeResponse;
import com.conovax.sexbody.application.dto.response.GymSubscriptionStatusResponse;
import com.conovax.sexbody.application.dto.response.PaymentMethodResponse;
import com.conovax.sexbody.application.dto.response.SaasPlanFeatureResponse;
import com.conovax.sexbody.application.dto.response.SaasPlanResponse;
import com.conovax.sexbody.domain.entities.GymPaymentOrder;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.ports.PaymentGatewayPort;
import com.conovax.sexbody.domain.ports.PaymentGatewayResult;
import com.conovax.sexbody.domain.ports.SubscriptionCommand;
import com.conovax.sexbody.domain.repositories.GymPaymentOrderRepository;
import com.conovax.sexbody.domain.repositories.GymRepository;
import com.conovax.sexbody.infrastructure.payment.PaymentGatewayRegistry;
import com.conovax.sexbody.infrastructure.payment.StripeService;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.Invoice;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GymSaasSubscriptionService {

	private final GymPaymentOrderRepository orderRepository;
	private final GymSubscriptionService gymSubscriptionService;
	private final SaasPlanService saasPlanService;
	private final GymRepository gymRepository;
	private final StripeService stripeService;
	private final PaymentGatewayRegistry gatewayRegistry;

	/**
	 * Lista todos los planes SaaS activos disponibles para que el gym los vea.
	 */
	@Transactional(readOnly = true)
	public List<SaasPlanResponse> getAvailablePlans() {
		return saasPlanService.getAll();
	}

	/**
	 * Inicia la suscripción recurrente del gym a un plan SaaS.
	 *
	 * Flujo (CARD/Stripe):
	 * 1. El frontend usa Stripe.js para tokenizar la tarjeta → obtiene un paymentMethodId (pm_xxx).
	 * 2. Este endpoint selecciona la pasarela según el paymentMethodType enviado.
	 * 3. La pasarela crea la suscripción y devuelve un resultado genérico.
	 * 4. La orden queda en PENDING hasta que la pasarela confirma el primer cobro via webhook.
	 *
	 * Para agregar una nueva pasarela (PSE, Nequi, etc.) solo se necesita:
	 *   - Implementar PaymentGatewayPort.
	 *   - Agregar el mapeo en PaymentGatewayRegistry.
	 */
	@Transactional
	public GymSubscribeResponse subscribe(Long gymId, GymSubscribeRequest request) {
		gymRepository.findByIdAndStatusTrue(gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + gymId));

		orderRepository.findLatestOrderByGymId(gymId).ifPresent(existing -> {
			if ("ACTIVE".equals(existing.getStatus())) {
				throw new IllegalStateException(
						"El gym ya tiene una suscripción activa. Cancélala antes de contratar un nuevo plan."
				);
			}
			if ("PENDING".equals(existing.getStatus())) {
				throw new IllegalStateException(
						"Ya hay una suscripción en proceso de verificación. Espera a que se confirme el pago antes de intentar de nuevo."
				);
			}
		});

		SaasPlanResponse plan = saasPlanService.getById(request.saasPlanId());

		if (plan.stripePriceId() == null || plan.stripePriceId().isBlank()) {
			throw new IllegalStateException(
					"El plan '" + plan.code() + "' no tiene un Price ID configurado. " +
					"Contacta al administrador."
			);
		}

		String paymentMethodType = request.paymentMethodType() != null
				? request.paymentMethodType().toUpperCase()
				: "CARD";

		PaymentGatewayPort gateway = gatewayRegistry.getGatewayForPaymentMethod(paymentMethodType);

		// Reutiliza el Customer de Stripe que ya tenga este gym (de una orden previa).
		// Evita crear/compartir customers por email y, por tanto, compartir tarjetas entre gimnasios.
		String existingCustomerId = orderRepository.findLatestOrderByGymId(gymId)
				.map(GymPaymentOrder::getStripeCustomerId)
				.filter(id -> id != null && !id.isBlank())
				.orElse(null);

		SubscriptionCommand command = SubscriptionCommand.of(
				gymId,
				plan.id(),
				plan.stripePriceId(),
				paymentMethodType,
				request.customerEmail(),
				existingCustomerId,
				request.paymentMethodId(),
				plan.price(),
				"COP"
		);

		PaymentGatewayResult result = gateway.createSubscription(command);

		long amountInCents = plan.price()
				.multiply(java.math.BigDecimal.valueOf(100))
				.longValue();

		GymPaymentOrder order = orderRepository.save(GymPaymentOrder.builder()
				.gymId(gymId)
				.saasPlanId(plan.id())
				.stripeSubscriptionId(result.subscriptionId())
				.stripeCustomerId(result.customerId())
				.paymentGateway(gateway.getGatewayCode())
				.paymentMethodType(paymentMethodType)
				.amountInCents(amountInCents)
				.currency("COP")
				.status(result.status())
				.customerEmail(request.customerEmail())
				.build());

		log.info("Orden {} creada para gym {} plan {} ({} / {})",
				order.getId(), gymId, plan.code(), gateway.getGatewayCode(), paymentMethodType);

		return new GymSubscribeResponse(
				order.getId(),
				result.subscriptionId(),
				result.redirectUrl(),
				order.getStatus(),
				"Suscripción creada. Se activará automáticamente al confirmar el primer pago."
		);
	}

	/**
	 * Consulta el estado actual de una orden de pago del gym.
	 */
	@Transactional(readOnly = true)
	public GymPaymentOrder getOrderById(Long gymId, Long orderId) {
		GymPaymentOrder order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + orderId));
		if (!gymId.equals(order.getGymId())) {
			throw new IllegalArgumentException("La orden no pertenece al gym indicado");
		}
		return order;
	}

	/**
	 * Procesa eventos de webhook recibidos desde Stripe.
	 *
	 * Eventos manejados:
	 *  - customer.subscription.updated → activa, suspende o cancela la suscripción del gym
	 *  - customer.subscription.deleted → cancela la suscripción del gym
	 */
	@Transactional
	public void handleStripeEvent(Event event) {
		switch (event.getType()) {
			case "customer.subscription.updated" -> handleSubscriptionUpdated(event);
			case "customer.subscription.deleted" -> handleSubscriptionDeleted(event);
			case "invoice.payment_succeeded"     -> handleInvoicePaymentSucceeded(event);
			default -> log.debug("Evento Stripe no manejado: {}", event.getType());
		}
	}

	private void handleSubscriptionUpdated(Event event) {
		Subscription sub = deserializeSubscription(event);
		if (sub == null) return;

		GymPaymentOrder order = orderRepository.findByStripeSubscriptionId(sub.getId()).orElse(null);
		if (order == null) {
			log.warn("No se encontró orden para Stripe sub: {}", sub.getId());
			return;
		}

		switch (sub.getStatus()) {
			case "active" -> {
				orderRepository.save(order.toBuilder().status("ACTIVE").build());
				activateSubscription(order.getGymId(), order.getSaasPlanId());
				log.info("Suscripción Stripe activada para gym {} (sub {})", order.getGymId(), sub.getId());
			}
			case "past_due" -> {
				orderRepository.save(order.toBuilder().status("PAST_DUE").build());
				log.info("Suscripción Stripe con pago vencido para gym {} (sub {})", order.getGymId(), sub.getId());
			}
			case "canceled", "unpaid" -> {
				orderRepository.save(order.toBuilder().status("CANCELLED").build());
				gymSubscriptionService.deactivateCurrentSubscription(order.getGymId());
				log.info("Suscripción Stripe cancelada/impaga para gym {}: {}", order.getGymId(), sub.getStatus());
			}
			default -> log.debug("Estado Stripe '{}' sin acción para sub {}", sub.getStatus(), sub.getId());
		}
	}

	private void handleSubscriptionDeleted(Event event) {
		Subscription sub = deserializeSubscription(event);
		if (sub == null) return;

		orderRepository.findByStripeSubscriptionId(sub.getId()).ifPresent(order -> {
			orderRepository.save(order.toBuilder().status("CANCELLED").build());
			gymSubscriptionService.deactivateCurrentSubscription(order.getGymId());
			log.info("Suscripción Stripe eliminada para gym {} (sub {})", order.getGymId(), sub.getId());
		});
	}

	/**
	 * invoice.payment_succeeded — se dispara cuando Stripe cobra exitosamente una factura.
	 * Activa la orden si aún está PENDING (primer cobro) o la mantiene ACTIVE en renovaciones.
	 */
	private void handleInvoicePaymentSucceeded(Event event) {
		String subscriptionId = extractSubscriptionIdFromInvoiceEvent(event);
		if (subscriptionId == null || subscriptionId.isBlank()) {
			log.debug("invoice.payment_succeeded sin subscriptionId, ignorado");
			return;
		}

		GymPaymentOrder order = orderRepository.findByStripeSubscriptionId(subscriptionId).orElse(null);
		if (order == null) {
			log.warn("invoice.payment_succeeded: no se encontró orden para sub {}", subscriptionId);
			return;
		}

		if ("PENDING".equals(order.getStatus())) {
			orderRepository.save(order.toBuilder().status("ACTIVE").build());
			activateSubscription(order.getGymId(), order.getSaasPlanId());
			log.info("Suscripción activada vía invoice.payment_succeeded para gym {} (sub {})",
					order.getGymId(), subscriptionId);
		} else {
			log.debug("invoice.payment_succeeded: orden {} ya en estado {}, sin cambio",
					order.getId(), order.getStatus());
		}
	}

	// -------------------------------------------------------------------------
	// Cancelación de suscripción
	// -------------------------------------------------------------------------

	/**
	 * Cancela la suscripción activa del gym.
	 *
	 * @param immediate true  = cancela ahora mismo en Stripe → orden pasa a CANCELLED
	 *                  false = cancela al final del período pagado → orden pasa a CANCEL_AT_PERIOD_END
	 */
	@Transactional
	public void cancelSubscription(Long gymId, boolean immediate) {
		GymPaymentOrder order = orderRepository.findLatestOrderByGymId(gymId)
				.orElseThrow(() -> new ResourceNotFoundException("El gym no tiene ninguna suscripción"));

		if ("CANCELLED".equals(order.getStatus())) {
			throw new IllegalStateException("La suscripción ya está cancelada.");
		}

		String subId = order.getStripeSubscriptionId();
		if (subId == null || subId.isBlank()) {
			throw new IllegalStateException("No se encontró el ID de suscripción en la pasarela.");
		}

		PaymentGatewayPort gateway = gatewayRegistry.getByCode(
				order.getPaymentGateway() != null ? order.getPaymentGateway() : "STRIPE"
		);
		gateway.cancelSubscription(subId, immediate);

		if (immediate) {
			orderRepository.save(order.toBuilder().status("CANCELLED").build());
			gymSubscriptionService.deactivateCurrentSubscription(gymId);
			log.info("Suscripción cancelada inmediatamente para gym {} (sub {})", gymId, subId);
		} else {
			orderRepository.save(order.toBuilder().status("CANCEL_AT_PERIOD_END").build());
			log.info("Suscripción programada para cancelar al fin del período para gym {} (sub {})", gymId, subId);
		}
	}

	// -------------------------------------------------------------------------
	// Estado de suscripción actual
	// -------------------------------------------------------------------------

	/**
	 * Devuelve el estado actual de la suscripción del gym.
	 * Prioridad:
	 *   1. Si existe una GymPaymentOrder (plan de pago Stripe) → devuelve ese estado.
	 *   2. Si no existe pero hay una GymSubscription interna (plan gratuito / trial) → devuelve
	 *      un GymSubscriptionStatusResponse construido a partir de ella, con sus features.
	 *   3. Si ninguna existe → devuelve null (204).
	 */
	@Transactional(readOnly = true)
	public GymSubscriptionStatusResponse getCurrentSubscription(Long gymId) {
		// 1. Buscar orden de pago Stripe
		var maybeOrder = orderRepository.findLatestOrderByGymId(gymId);
		if (maybeOrder.isPresent()) {
			GymPaymentOrder order = maybeOrder.get();
			SaasPlanResponse plan = saasPlanService.getById(order.getSaasPlanId());
			return new GymSubscriptionStatusResponse(
					order.getId(),
					order.getStatus(),
					plan.id(),
					plan.name(),
					plan.description(),
					plan.price(),
					plan.numDays(),
					order.getStripeSubscriptionId(),
					order.getPaymentGateway() != null ? order.getPaymentGateway() : "STRIPE",
					order.getPaymentMethodType() != null ? order.getPaymentMethodType() : "CARD",
					order.getCustomerEmail(),
					order.getCreatedAt(),
					plan.features() != null ? plan.features() : List.of()
			);
		}

		// 2. Fallback: suscripción interna (plan gratuito o período de prueba)
		var internalSub = gymSubscriptionService.getCurrentByGymId(gymId);
		if (internalSub == null || (!Boolean.TRUE.equals(internalSub.active()) && internalSub.id() == null)) {
			return null;
		}

		String status = Boolean.TRUE.equals(internalSub.active()) ? "ACTIVE" : "INACTIVE";
		String planName = internalSub.planName() != null ? internalSub.planName() : "Período de prueba";
		List<SaasPlanFeatureResponse> features = internalSub.planFeatures() != null
				? internalSub.planFeatures()
				: List.of();

		java.time.LocalDateTime createdAt = internalSub.createdAt() != null
				? internalSub.createdAt()
				: (internalSub.startDate() != null ? internalSub.startDate().atStartOfDay() : null);

		java.math.BigDecimal price = null;
		Long numDays = 0L;
		String description = null;
		if (internalSub.saasPlanId() != null) {
			try {
				SaasPlanResponse plan = saasPlanService.getById(internalSub.saasPlanId());
				price = plan.price();
				numDays = plan.numDays();
				description = plan.description();
			} catch (Exception ignored) { }
		}

		return new GymSubscriptionStatusResponse(
				internalSub.id(),
				status,
				internalSub.saasPlanId(),
				planName,
				description,
				price,
				numDays,
				null,
				null,
				null,
				null,
				createdAt,
				features
		);
	}

	// -------------------------------------------------------------------------
	// Gestión de métodos de pago
	// -------------------------------------------------------------------------

	/**
	 * Lista las tarjetas registradas en Stripe para el gym.
	 * Requiere que el gym tenga al menos una orden activa con stripeCustomerId.
	 */
	@Transactional(readOnly = true)
	public List<PaymentMethodResponse> listPaymentMethods(Long gymId) {
		GymPaymentOrder order = getOrderForPaymentMethods(gymId);
		String defaultPmId = stripeService.getSubscriptionDefaultPaymentMethod(order.getStripeSubscriptionId());
		List<PaymentMethod> stripeList = stripeService.listPaymentMethods(order.getStripeCustomerId(), defaultPmId);
		return stripeList.stream()
				.map(pm -> toPaymentMethodResponse(pm, defaultPmId))
				.toList();
	}

	/**
	 * Agrega una nueva tarjeta al gym y la establece como método de pago predeterminado.
	 * El paymentMethodId (pm_xxx) debe obtenerse en el frontend con Stripe.js.
	 */
	@Transactional
	public List<PaymentMethodResponse> addPaymentMethod(Long gymId, AddPaymentMethodRequest request) {
		GymPaymentOrder order = getOrderForPaymentMethods(gymId);
		stripeService.addPaymentMethodAndSetDefault(
				order.getStripeCustomerId(),
				request.paymentMethodId(),
				order.getStripeSubscriptionId()
		);
		log.info("Método de pago {} agregado como predeterminado para gimnasio {}", request.paymentMethodId(), gymId);
		return listPaymentMethods(gymId);
	}

	/**
	 * Establece una tarjeta existente como método de pago predeterminado para los cobros automáticos.
	 */
	@Transactional
	public List<PaymentMethodResponse> setDefaultPaymentMethod(Long gymId, String paymentMethodId) {
		GymPaymentOrder order = getOrderForPaymentMethods(gymId);
		stripeService.setDefaultPaymentMethod(
				order.getStripeCustomerId(),
				paymentMethodId,
				order.getStripeSubscriptionId()
		);
		log.info("Método de pago {} establecido como predeterminado para gimnasio {}", paymentMethodId, gymId);
		return listPaymentMethods(gymId);
	}

	/**
	 * Desvincula una tarjeta del Customer en Stripe.
	 * No se puede eliminar el método de pago predeterminado activo.
	 */
	@Transactional
	public void removePaymentMethod(Long gymId, String paymentMethodId) {
		GymPaymentOrder order = getOrderForPaymentMethods(gymId);
		String defaultPmId = stripeService.getSubscriptionDefaultPaymentMethod(order.getStripeSubscriptionId());
		if (paymentMethodId.equals(defaultPmId)) {
			throw new IllegalStateException(
					"No puedes eliminar el método de pago predeterminado. Establece otro antes de eliminarlo."
			);
		}
		stripeService.detachPaymentMethod(paymentMethodId);
		log.info("Método de pago {} eliminado para el gimnasio con ID {}", paymentMethodId, gymId);
	}

	// -------------------------------------------------------------------------
	// Helpers privados
	// -------------------------------------------------------------------------

	/**
	 * Extrae el subscriptionId de un evento invoice.* usando primero la deserialización
	 * estándar del SDK y, si falla (versión del evento distinta), parsea el JSON crudo.
	 */
	private String extractSubscriptionIdFromInvoiceEvent(Event event) {
		// 1. Intento estándar
		EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
		if (deserializer.getObject().isPresent()) {
			return ((Invoice) deserializer.getObject().get()).getSubscription();
		}
		// 2. Fallback: JSON crudo (ocurre cuando la API version del evento difiere del SDK)
		try {
			com.fasterxml.jackson.databind.JsonNode root =
					new com.fasterxml.jackson.databind.ObjectMapper().readTree(event.toJson());
			com.fasterxml.jackson.databind.JsonNode subNode =
					root.path("data").path("object").path("subscription");
			if (!subNode.isMissingNode() && !subNode.isNull()) {
				String subId = subNode.asText();
				log.debug("subscriptionId extraído del JSON crudo: {}", subId);
				return subId;
			}
		} catch (Exception ex) {
			log.warn("No se pudo extraer subscriptionId del evento invoice: {}", ex.getMessage());
		}
		return null;
	}

	private Subscription deserializeSubscription(Event event) {
		EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
		if (deserializer.getObject().isEmpty()) {
			log.warn("No se pudo deserializar Subscription del evento Stripe {}", event.getType());
			return null;
		}
		return (Subscription) deserializer.getObject().get();
	}

	private void activateSubscription(Long gymId, Long saasPlanId) {
		SaasPlanResponse plan = saasPlanService.getById(saasPlanId);
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = computeEndDate(startDate, plan.numDays());
		gymSubscriptionService.create(gymId, new GymSubscriptionRequest(
				saasPlanId, startDate, endDate, Boolean.TRUE, "Activación automática viá Stripe"
		));
	}

	/**
	 * Calcula la fecha de fin rolling desde la fecha real del pago.
	 * Pagando el 27 abril: MONTHLY → 26 mayo | ANNUAL → 26 abril año siguiente
	 */
	private LocalDate computeEndDate(LocalDate start, Long numDays) {
        return start.plusDays(numDays).minusDays(1);
	}
	/**
	 * Obtiene la orden ACTIVE más reciente del gym.
	 * Lanza excepción si no existe (gym sin suscripción activa).
	 */
	private GymPaymentOrder getActiveOrder(Long gymId) {
		return orderRepository.findLatestActiveByGymId(gymId)
				.orElseThrow(() -> new IllegalStateException(
						"El gimnasio no tiene una suscripción activa en Stripe. " +
						"Debe suscribirse primero a un plan."
				));
	}

	/**
	 * Obtiene la última orden del gym (cualquier estado) para operaciones con métodos de pago.
	 * El stripeCustomerId sigue siendo válido en Stripe aunque la suscripción esté cancelada.
	 */
	private GymPaymentOrder getOrderForPaymentMethods(Long gymId) {
		GymPaymentOrder order = orderRepository.findLatestOrderByGymId(gymId)
				.orElseThrow(() -> new IllegalStateException(
						"El gimnasio no tiene ninguna orden de pago registrada."
				));
		if (order.getStripeCustomerId() == null || order.getStripeCustomerId().isBlank()) {
			throw new IllegalStateException("El gimnasio no tiene un cliente registrado en Stripe.");
		}
		return order;
	}

	private PaymentMethodResponse toPaymentMethodResponse(PaymentMethod pm, String defaultPmId) {
		PaymentMethod.Card card = pm.getCard();
		return new PaymentMethodResponse(
				pm.getId(),
				card != null ? card.getBrand() : null,
				card != null ? card.getLast4() : null,
				card != null ? card.getExpMonth() : null,
				card != null ? card.getExpYear() : null,
				pm.getId().equals(defaultPmId)
		);
	}}

