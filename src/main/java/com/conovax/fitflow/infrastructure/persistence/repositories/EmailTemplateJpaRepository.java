package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.EmailTemplateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailTemplateJpaRepository extends JpaRepository<EmailTemplateJpaEntity, Long> {
	Optional<EmailTemplateJpaEntity> findByCode(String code);
}
