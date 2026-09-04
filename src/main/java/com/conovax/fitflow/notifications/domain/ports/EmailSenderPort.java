package com.conovax.fitflow.notifications.domain.ports;

/**
 * Puerto de salida para envío de emails.
 * Cada proveedor (Mailgun, SendGrid, SES, etc.) implementa esta interfaz.
 */
public interface EmailSenderPort {

	/** Código único del proveedor. Ej: "MAILGUN". */
	String getProviderCode();

	/**
	 * Envía un email.
	 *
	 * @param message datos del mensaje a enviar
	 */
	void send(EmailMessage message);
}
