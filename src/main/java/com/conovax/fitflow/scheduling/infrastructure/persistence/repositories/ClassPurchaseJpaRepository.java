package com.conovax.fitflow.scheduling.infrastructure.persistence.repositories;

import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassPurchaseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClassPurchaseJpaRepository extends JpaRepository<ClassPurchaseJpaEntity, Long> {
    Optional<ClassPurchaseJpaEntity> findByIdAndGymId(Long id, Long gymId);
    boolean existsByGymIdAndSessionIdAndUserGymIdAndStatus(Long gymId, Long sessionId, Long userGymId, String status);
}
