package com.conovax.fitflow.scheduling.infrastructure.persistence.repositories;

import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClassReservationJpaRepository extends JpaRepository<ClassReservationJpaEntity, Long> {
    Optional<ClassReservationJpaEntity> findByIdAndGymId(Long id, Long gymId);
    Optional<ClassReservationJpaEntity> findByGymIdAndSessionIdAndUserGymIdAndStatusIn(Long gymId, Long sessionId, Long userGymId, List<String> statuses);
    long countByGymIdAndSessionIdAndStatusIn(Long gymId, Long sessionId, List<String> statuses);
    List<ClassReservationJpaEntity> findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(Long gymId, Long sessionId, String status);
    List<ClassReservationJpaEntity> findAllByGymIdAndSessionIdOrderByReservedAtAsc(Long gymId, Long sessionId);
    List<ClassReservationJpaEntity> findAllByGymIdAndUserGymIdOrderByReservedAtDesc(Long gymId, Long userGymId);
}
