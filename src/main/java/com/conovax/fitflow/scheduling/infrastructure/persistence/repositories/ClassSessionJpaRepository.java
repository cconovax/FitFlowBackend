package com.conovax.fitflow.scheduling.infrastructure.persistence.repositories;

import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassSessionJpaEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClassSessionJpaRepository extends JpaRepository<ClassSessionJpaEntity, Long> {
    Optional<ClassSessionJpaEntity> findByIdAndGymId(Long id, Long gymId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from ClassSessionJpaEntity s where s.id = :id and s.gymId = :gymId")
    Optional<ClassSessionJpaEntity> lockByIdAndGymId(@Param("id") Long id, @Param("gymId") Long gymId);
    List<ClassSessionJpaEntity> findAllByGymIdAndTrainerUserGymIdAndStartsAtBetweenOrderByStartsAtAsc(Long gymId, Long trainerUserGymId, LocalDateTime from, LocalDateTime to);
    List<ClassSessionJpaEntity> findAllByGymIdAndScheduleIdAndStatusAndStartsAtAfter(Long gymId, Long scheduleId, String status, LocalDateTime after);
    List<ClassSessionJpaEntity> findAllByGymIdAndStartsAtBetweenOrderByStartsAtAsc(Long gymId, LocalDateTime from, LocalDateTime to);
    boolean existsByGymIdAndScheduleIdAndStartsAt(Long gymId, Long scheduleId, LocalDateTime startsAt);
}
