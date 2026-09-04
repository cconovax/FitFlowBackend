package com.conovax.fitflow.notifications.domain.repositories;

import com.conovax.fitflow.notifications.domain.entities.EmailTemplate;

import java.util.Optional;

public interface EmailTemplateRepository {
	Optional<EmailTemplate> findByCode(String code);
	EmailTemplate save(EmailTemplate template);
}
