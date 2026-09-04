package com.conovax.fitflow.notifications.infrastructure.config;

import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;

/**
 * Configuración de Freemarker para procesamiento de plantillas de email en memoria.
 * No expone un ViewResolver — solo se usa desde {@code EmailService}.
 */
@org.springframework.context.annotation.Configuration
public class EmailConfig {

	@Bean
	public freemarker.template.Configuration freemarkerConfiguration() {
		freemarker.template.Configuration cfg =
				new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_34);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		return cfg;
	}
}
