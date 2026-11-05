package com.conovax.fitflow.scheduling.infrastructure.persistence.repositories;

import com.conovax.fitflow.scheduling.infrastructure.persistence.entities.ClassReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ClassReservationJpaRepository extends JpaRepository<ClassReservationJpaEntity, Long> {
    Optional<ClassReservationJpaEntity> findByIdAndGymId(Long id, Long gymId);
    @Query("select r.sessionId from ClassReservationJpaEntity r where r.id = :id and r.gymId = :gymId")
    Optional<Long> findSessionIdByIdAndGymId(@Param("id") Long id, @Param("gymId") Long gymId);
    List<ClassReservationJpaEntity> findAllByGymIdAndSessionIdAndStatusInOrderByReservedAtAsc(Long gymId, Long sessionId, Collection<String> statuses);
    List<ClassReservationJpaEntity> findAllByGymIdAndSessionIdInAndStatusIn(Long gymId, Collection<Long> sessionIds, Collection<String> statuses);
    List<ClassReservationJpaEntity> findAllByStatusAndOfferExpiresAtLessThanEqual(String status, LocalDateTime at);
    Optional<ClassReservationJpaEntity> findByGymIdAndSessionIdAndUserGymIdAndStatusIn(Long gymId, Long sessionId, Long userGymId, List<String> statuses);
    long countByGymIdAndSessionIdAndStatusIn(Long gymId, Long sessionId, List<String> statuses);
    List<ClassReservationJpaEntity> findAllByGymIdAndSessionIdAndStatusOrderByWaitlistPositionAsc(Long gymId, Long sessionId, String status);
    List<ClassReservationJpaEntity> findAllByGymIdAndSessionIdOrderByReservedAtAsc(Long gymId, Long sessionId);
    List<ClassReservationJpaEntity> findAllByGymIdAndUserGymIdOrderByReservedAtDesc(Long gymId, Long userGymId);
}
