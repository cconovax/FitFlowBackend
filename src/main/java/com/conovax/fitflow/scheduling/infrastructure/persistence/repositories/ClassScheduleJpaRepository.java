package com.conovax.fitflow.scheduling.infrastructure.persistence.repositories;

import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClassScheduleJpaRepository extends JpaRepository<ClassScheduleJpaEntity, Long> {
    List<ClassScheduleJpaEntity> findAllByGymIdOrderByWeekdayAscStartTimeAsc(Long gymId);
    List<ClassScheduleJpaEntity> findAllByStatus(String status);
    Optional<ClassScheduleJpaEntity> findByIdAndGymId(Long id, Long gymId);
}
