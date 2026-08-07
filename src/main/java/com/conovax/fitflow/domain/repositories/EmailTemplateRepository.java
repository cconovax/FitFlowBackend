package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.EmailTemplate;

import java.util.Optional;

public interface EmailTemplateRepository {
	Optional<EmailTemplate> findByCode(String code);
	EmailTemplate save(EmailTemplate template);
}
