package com.conovax.fitflow.infrastructure.email;

import com.conovax.fitflow.domain.ports.EmailMessage;
import com.conovax.fitflow.domain.ports.EmailSenderPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Adaptador de envío de emails usando la API HTTP de Mailgun.
 * Para cambiar de proveedor, implementa {@link EmailSenderPort} con otro adaptador
 * y reemplaza el bean activo sin modificar el dominio ni la capa de aplicación.
 */
@Slf4j
@Component
public class MailgunEmailAdapter implements EmailSenderPort {

	private final RestClient restClient;
	private final String domain;
	private final String fromAddress;

	public MailgunEmailAdapter(
			@Value("${mailgun.api-key}") String apiKey,
			@Value("${mailgun.domain}") String domain,
			@Value("${mailgun.from}") String fromAddress,
			@Value("${mailgun.base-url:https://api.mailgun.net/v3}") String baseUrl) {
		this.domain = domain;
		this.fromAddress = fromAddress;
		String credentials = Base64.getEncoder()
				.encodeToString(("api:" + apiKey).getBytes(StandardCharsets.UTF_8));
		this.restClient = RestClient.builder()
				.baseUrl(baseUrl)
				.defaultHeader("Authorization", "Basic " + credentials)
				.build();
	}

	@Override
	public String getProviderCode() {
		return "MAILGUN";
	}

	@Override
	public void send(EmailMessage message) {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("from", fromAddress);
		form.add("to", message.to());
		form.add("subject", message.subject());
		form.add("html", message.htmlBody());

		log.debug("Sending email via Mailgun — domain: {}, from: {}, to: {}", domain, fromAddress, message.to());

		restClient.post()
				.uri("/{domain}/messages", domain)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(form)
				.retrieve()
				.onStatus(HttpStatusCode::isError, (req, res) -> {
					String body;
					try {
						body = new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8);
					} catch (IOException ex) {
						body = "(no response body)";
					}
					String hint = buildHint(res.getStatusCode().value(), body);
					throw new RuntimeException(
							"Mailgun error [" + res.getStatusCode() + "] domain=" + domain + " — " + body + hint);
				})
				.toBodilessEntity();
	}

	/** Agrega una pista de solución según el código HTTP de Mailgun. */
	private String buildHint(int status, String body) {
		if (status == 401 || status == 403) {
			return " | POSIBLES CAUSAS: (1) El dominio '" + domain + "' no está registrado/verificado en Mailgun. "
					+ "(2) Cuenta en región EU — cambia MAILGUN_BASE_URL a https://api.eu.mailgun.net/v3. "
					+ "(3) La API key no tiene permisos sobre este dominio.";
		}
		if (status == 400 && body.contains("sandbox")) {
			return " | PISTA: Dominio sandbox — solo puede enviar a destinatarios autorizados en el dashboard de Mailgun.";
		}
		return "";
	}
}
