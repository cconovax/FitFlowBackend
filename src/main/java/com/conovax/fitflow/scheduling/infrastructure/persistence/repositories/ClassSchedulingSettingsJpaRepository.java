package com.conovax.fitflow.scheduling.infrastructure.persistence.repositories;

import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassSchedulingSettingsJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassSchedulingSettingsJpaRepository extends JpaRepository<ClassSchedulingSettingsJpaEntity, Long> {
}
