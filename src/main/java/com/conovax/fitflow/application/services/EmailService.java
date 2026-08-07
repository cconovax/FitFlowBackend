package com.conovax.fitflow.application.services;

import com.conovax.fitflow.domain.entities.EmailTemplate;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.ports.EmailMessage;
import com.conovax.fitflow.domain.ports.EmailSenderPort;
import com.conovax.fitflow.domain.repositories.EmailTemplateRepository;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

	private final EmailTemplateRepository emailTemplateRepository;
	private final EmailSenderPort emailSenderPort;
	private final freemarker.template.Configuration freemarkerConfiguration;

	/**
	 * Carga la plantilla por código, reemplaza variables con Freemarker y envía el email.
	 *
	 * @param to           destinatario
	 * @param templateCode código único de la plantilla en la BD
	 * @param variables    mapa de variables para inyectar en la plantilla
	 */
	public void sendEmail(String to, String templateCode, Map<String, Object> variables) {
		EmailTemplate template = emailTemplateRepository.findByCode(templateCode)
				.orElseThrow(() -> new ResourceNotFoundException("Email template not found with code: " + templateCode));

		if (!Boolean.TRUE.equals(template.getEnabled())) {
			log.warn("Email template '{}' is disabled, skipping send to {}", templateCode, to);
			return;
		}

		String renderedSubject = render(template.getSubject(), variables, templateCode + "_subject");
		String renderedBody    = render(template.getHtml(), variables, templateCode + "_html");

		emailSenderPort.send(new EmailMessage(to, renderedSubject, renderedBody));
		log.info("Email '{}' sent to {} via {}", templateCode, to, emailSenderPort.getProviderCode());
	}

	private String render(String content, Map<String, Object> variables, String templateName) {
		try {
			Template freemarkerTemplate = new Template(templateName, new StringReader(content), freemarkerConfiguration);
			StringWriter writer = new StringWriter();
			freemarkerTemplate.process(variables, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException("Failed to render email template: " + templateName, e);
		}
	}
}
