package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.EmailTemplate;
import com.conovax.fitflow.infrastructure.persistence.entities.EmailTemplateJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplateMapper {

	public EmailTemplate toDomain(EmailTemplateJpaEntity e) {
		if (e == null) return null;
		return EmailTemplate.builder()
				.id(e.getId())
				.code(e.getCode())
				.subject(e.getSubject())
				.html(e.getHtml())
				.enabled(e.getEnabled())
				.createdAt(e.getCreatedAt())
				.updatedAt(e.getUpdatedAt())
				.build();
	}

	public EmailTemplateJpaEntity toJpaEntity(EmailTemplate d) {
		if (d == null) return null;
		return EmailTemplateJpaEntity.builder()
				.id(d.getId())
				.code(d.getCode())
				.subject(d.getSubject())
				.html(d.getHtml())
				.enabled(d.getEnabled())
				.build();
	}
}
