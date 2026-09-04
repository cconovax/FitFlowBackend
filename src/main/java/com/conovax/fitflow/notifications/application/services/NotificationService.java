package com.conovax.fitflow.notifications.application.services;

import com.conovax.fitflow.notifications.domain.ports.NotificationChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Punto de entrada único del módulo de notificaciones.
 *
 * <p>Los demás módulos (auth, gym, membership, subscription...) no deben depender
 * directamente de {@link EmailService} ni de proveedores concretos: deben usar esta
 * fachada para que el canal de envío (email, SMS, WhatsApp) sea intercambiable.</p>
 *
 * <p>Hoy solo {@link NotificationChannel#EMAIL} tiene una implementación activa.
 * Para agregar un nuevo canal: crea el servicio correspondiente (ej. {@code SmsService}),
 * inyéctalo aquí y agrega su caso en {@link #notify}.</p>
 */
@Service
@RequiredArgsConstructor
public class NotificationService {

	private final EmailService emailService;

	/**
	 * Envía una notificación por el canal indicado usando una plantilla registrada.
	 *
	 * @param channel      canal de envío (hoy solo EMAIL está implementado)
	 * @param to           destinatario (email, número de teléfono, etc. según el canal)
	 * @param templateCode código único de la plantilla
	 * @param variables    variables a inyectar en la plantilla
	 */
	public void notify(NotificationChannel channel, String to, String templateCode, Map<String, Object> variables) {
		switch (channel) {
			case EMAIL -> emailService.sendEmail(to, templateCode, variables);
			case SMS -> throw new UnsupportedOperationException("El canal SMS aún no está implementado");
			case WHATSAPP -> throw new UnsupportedOperationException("El canal WhatsApp aún no está implementado");
		}
	}

	/** Atajo para el canal EMAIL, el único soportado actualmente. */
	public void sendEmail(String to, String templateCode, Map<String, Object> variables) {
		notify(NotificationChannel.EMAIL, to, templateCode, variables);
	}
}
