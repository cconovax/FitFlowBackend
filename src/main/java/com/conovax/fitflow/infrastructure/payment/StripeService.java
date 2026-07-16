package com.conovax.sexbody.infrastructure.payment;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Subscription;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.param.PaymentMethodListParams;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.SubscriptionUpdateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Adaptador hacia la API de Stripe.
 * Documentación: https://stripe.com/docs/api
 */
@Slf4j
@Service
public class StripeService {

	@Value("${stripe.secret-key}")
	private String secretKey;

	@Value("${stripe.webhook.secret}")
	private String webhookSecret;

	private void init() {
		Stripe.apiKey = secretKey;
	}

	/**
	 * Obtiene o crea el Customer de Stripe asociado a un gimnasio.
	 *
	 * <p>Si el gym ya tiene un customerId previo (guardado en una orden anterior) se reutiliza;
	 * de lo contrario se crea un Customer NUEVO con el gymId en la metadata.</p>
	 *
	 * <p>Importante: NO se busca el customer por email a nivel global. Hacerlo provocaba que
	 * varios gimnasios registrados con el mismo email (p.ej. el del dueño) compartieran el mismo
	 * Customer en Stripe y, por tanto, las mismas tarjetas.</p>
	 *
	 * @param gymId              ID del gimnasio dueño del Customer
	 * @param existingCustomerId customerId previo del gym (cus_xxx) o null/blank si no tiene
	 * @param email              email para asociar al Customer al crearlo
	 * @return customerId (cus_xxx) exclusivo del gym
	 */
	public String getOrCreateGymCustomer(Long gymId, String existingCustomerId, String email) {
		init();
		try {
			if (existingCustomerId != null && !existingCustomerId.isBlank()) {
				return existingCustomerId;
			}
			CustomerCreateParams createParams = CustomerCreateParams.builder()
					.setEmail(email)
					.putMetadata("gymId", String.valueOf(gymId))
					.build();
			return Customer.create(createParams).getId();
		} catch (StripeException ex) {
			log.error("Error al crear/obtener cliente en Stripe para gym {}: {}", gymId, ex.getMessage());
			throw new IllegalStateException("No se pudo gestionar el cliente en Stripe", ex);
		}
	}

	/**
	 * Asocia un PaymentMethod (pm_xxx) a un Customer (cus_xxx).
	 * Debe llamarse antes de crear la suscripción.
	 */
	public void attachPaymentMethod(String customerId, String paymentMethodId) {
		init();
		try {
			PaymentMethod pm = PaymentMethod.retrieve(paymentMethodId);
			PaymentMethodAttachParams params = PaymentMethodAttachParams.builder()
					.setCustomer(customerId)
					.build();
			pm.attach(params);
		} catch (StripeException ex) {
			log.error("Error al asociar método de pago en Stripe: {}", ex.getMessage());
			throw new IllegalStateException("No se pudo asociar el método de pago", ex);
		}
	}

	/**
	 * Crea una suscripción recurrente en Stripe.
	 * El priceId (price_xxx) debe estar pre-configurado en el dashboard de Stripe
	 * y almacenado en el campo stripePriceId del plan SaaS.
	 *
	 * @return subscriptionId (sub_xxx)
	 */
	public String createSubscription(String customerId, String priceId, String paymentMethodId) {
		init();
		try {
			SubscriptionCreateParams params = SubscriptionCreateParams.builder()
					.setCustomer(customerId)
					.addItem(SubscriptionCreateParams.Item.builder()
							.setPrice(priceId)
							.build())
					.setDefaultPaymentMethod(paymentMethodId)
					.build();
			Subscription subscription = Subscription.create(params);
			return subscription.getId();
		} catch (StripeException ex) {
			log.error("Error al crear suscripción en Stripe: {}", ex.getMessage());
			throw new IllegalStateException("No se pudo crear la suscripción en Stripe", ex);
		}
	}

	/**
	 * Verifica la firma del webhook enviado por Stripe y construye el Event.
	 * Stripe requiere el rawBody (String) sin parsear.
	 *
	 * @throws SecurityException si la firma no es válida
	 */
	public Event constructWebhookEvent(String rawBody, String sigHeader) {
		try {
			return Webhook.constructEvent(rawBody, sigHeader, webhookSecret);
		} catch (SignatureVerificationException ex) {
			log.warn("Firma de webhook Stripe inválida: {}", ex.getMessage());
			throw new SecurityException("Firma de webhook Stripe inválida", ex);
		} catch (Exception ex) {
			log.error("Error al construir evento Stripe: {}", ex.getMessage());
			throw new IllegalStateException("Error al procesar webhook de Stripe", ex);
		}
	}

	/**
	 * Lista los métodos de pago (tarjetas) de un Customer en Stripe.
	 * Marca como isDefault el que coincida con el default_payment_method de la suscripción.
	 *
	 * @param customerId cus_xxx
	 * @param defaultPaymentMethodId pm_xxx predeterminado (puede ser null)
	 */
	public List<PaymentMethod> listPaymentMethods(String customerId, String defaultPaymentMethodId) {
		init();
		try {
			PaymentMethodListParams params = PaymentMethodListParams.builder()
					.setCustomer(customerId)
					.setType(PaymentMethodListParams.Type.CARD)
					.build();
			return PaymentMethod.list(params).getData();
		} catch (StripeException ex) {
			log.error("Error al listar métodos de pago en Stripe: {}", ex.getMessage());
			throw new IllegalStateException("No se pudieron listar los métodos de pago", ex);
		}
	}

	/**
	 * Asocia un nuevo PaymentMethod al Customer y lo establece como predeterminado
	 * en el Customer y en la suscripción activa.
	 *
	 * @param customerId cus_xxx
	 * @param paymentMethodId pm_xxx nuevo
	 * @param stripeSubscriptionId sub_xxx — se actualiza su default_payment_method
	 */
	public void addPaymentMethodAndSetDefault(String customerId, String paymentMethodId, String stripeSubscriptionId) {
		init();
		try {
			// 1. Asociar al customer
			PaymentMethod pm = PaymentMethod.retrieve(paymentMethodId);
			pm.attach(PaymentMethodAttachParams.builder().setCustomer(customerId).build());

			// 2. Establecer como default en el customer (para futuras suscripciones)
			Customer customer = Customer.retrieve(customerId);
			customer.update(CustomerUpdateParams.builder()
					.setInvoiceSettings(CustomerUpdateParams.InvoiceSettings.builder()
							.setDefaultPaymentMethod(paymentMethodId)
							.build())
					.build());

			// 3. Actualizar la suscripción activa para que use este método
			if (stripeSubscriptionId != null && !stripeSubscriptionId.isBlank()) {
				Subscription subscription = Subscription.retrieve(stripeSubscriptionId);
				subscription.update(SubscriptionUpdateParams.builder()
						.setDefaultPaymentMethod(paymentMethodId)
						.build());
			}
		} catch (StripeException ex) {
			log.error("Error al agregar método de pago en Stripe: {}", ex.getMessage());
			throw new IllegalStateException("No se pudo agregar el método de pago", ex);
		}
	}

	/**
	 * Establece un PaymentMethod existente como predeterminado en el Customer y en su suscripción activa.
	 *
	 * @param customerId cus_xxx
	 * @param paymentMethodId pm_xxx a establecer como default
	 * @param stripeSubscriptionId sub_xxx
	 */
	public void setDefaultPaymentMethod(String customerId, String paymentMethodId, String stripeSubscriptionId) {
		init();
		try {
			Customer customer = Customer.retrieve(customerId);
			customer.update(CustomerUpdateParams.builder()
					.setInvoiceSettings(CustomerUpdateParams.InvoiceSettings.builder()
							.setDefaultPaymentMethod(paymentMethodId)
							.build())
					.build());

			if (stripeSubscriptionId != null && !stripeSubscriptionId.isBlank()) {
				Subscription subscription = Subscription.retrieve(stripeSubscriptionId);
				subscription.update(SubscriptionUpdateParams.builder()
						.setDefaultPaymentMethod(paymentMethodId)
						.build());
			}
		} catch (StripeException ex) {
			log.error("Error al establecer método de pago predeterminado en Stripe: {}", ex.getMessage());
			throw new IllegalStateException("No se pudo establecer el método de pago predeterminado", ex);
		}
	}

	/**
	 * Desvincula un PaymentMethod del Customer.
	 *
	 * @param paymentMethodId pm_xxx a eliminar
	 */
	public void detachPaymentMethod(String paymentMethodId) {
		init();
		try {
			PaymentMethod pm = PaymentMethod.retrieve(paymentMethodId);
			pm.detach();
		} catch (StripeException ex) {
			log.error("Error al desvincular método de pago en Stripe: {}", ex.getMessage());
			throw new IllegalStateException("No se pudo eliminar el método de pago", ex);
		}
	}

	/**
	 * Cancela una suscripción en Stripe.
	 *
	 * @param stripeSubscriptionId sub_xxx
	 * @param immediate true = cancela ahora; false = cancela al final del período pagado
	 */
	public void cancelSubscription(String stripeSubscriptionId, boolean immediate) {
		init();
		try {
			Subscription sub = Subscription.retrieve(stripeSubscriptionId);
			if (immediate) {
				sub.cancel();
			} else {
				sub.update(SubscriptionUpdateParams.builder()
						.setCancelAtPeriodEnd(true)
						.build());
			}
			log.info("Suscripción {} cancelada (immediate={})", stripeSubscriptionId, immediate);
		} catch (StripeException ex) {
			log.error("Error al cancelar suscripción en Stripe: {}", ex.getMessage());
			throw new IllegalStateException("No se pudo cancelar la suscripción en Stripe", ex);
		}
	}

	/**
	 * Obtiene el default_payment_method de una suscripción.
	 * Retorna null si la suscripción no tiene uno configurado.
	 */
	public String getSubscriptionDefaultPaymentMethod(String stripeSubscriptionId) {
		if (stripeSubscriptionId == null || stripeSubscriptionId.isBlank()) return null;
		init();
		try {
			Subscription sub = Subscription.retrieve(stripeSubscriptionId);
			return sub.getDefaultPaymentMethod();
		} catch (StripeException ex) {
			log.warn("No se pudo obtener el default_payment_method de la suscripción {}: {}", stripeSubscriptionId, ex.getMessage());
			return null;
		}
	}
}
