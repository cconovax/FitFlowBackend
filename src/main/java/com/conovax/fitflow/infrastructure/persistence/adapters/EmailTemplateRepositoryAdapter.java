package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.EmailTemplate;
import com.conovax.fitflow.domain.repositories.EmailTemplateRepository;
import com.conovax.fitflow.infrastructure.persistence.mappers.EmailTemplateMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.EmailTemplateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailTemplateRepositoryAdapter implements EmailTemplateRepository {

	private final EmailTemplateJpaRepository jpaRepository;
	private final EmailTemplateMapper mapper;

	@Override
	public Optional<EmailTemplate> findByCode(String code) {
		return jpaRepository.findByCode(code).map(mapper::toDomain);
	}

	@Override
	public EmailTemplate save(EmailTemplate template) {
		return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(template)));
	}
}
