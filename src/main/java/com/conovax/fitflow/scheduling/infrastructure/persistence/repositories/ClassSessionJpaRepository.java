package com.conovax.fitflow.scheduling.infrastructure.persistence.repositories;

import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassSessionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClassSessionJpaRepository extends JpaRepository<ClassSessionJpaEntity, Long> {
    Optional<ClassSessionJpaEntity> findByIdAndGymId(Long id, Long gymId);
    List<ClassSessionJpaEntity> findAllByGymIdAndStartsAtBetweenOrderByStartsAtAsc(Long gymId, LocalDateTime from, LocalDateTime to);
    boolean existsByGymIdAndScheduleIdAndStartsAt(Long gymId, Long scheduleId, LocalDateTime startsAt);
}
