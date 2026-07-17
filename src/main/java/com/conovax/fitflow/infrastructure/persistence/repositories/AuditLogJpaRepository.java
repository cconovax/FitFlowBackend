package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.AuditLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogJpaRepository
		extends JpaRepository<AuditLogJpaEntity, Long>, JpaSpecificationExecutor<AuditLogJpaEntity> {
}
