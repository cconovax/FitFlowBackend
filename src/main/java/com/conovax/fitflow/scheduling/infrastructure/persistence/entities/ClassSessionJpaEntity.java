package com.conovax.fitflow.scheduling.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "class_sessions")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ClassSessionJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id") private Long id;
    @Column(name = "gym_id", nullable = false) private Long gymId;
    @Column(name = "schedule_id", nullable = false) private Long scheduleId;
    @Column(name = "starts_at", nullable = false) private LocalDateTime startsAt;
    @Column(name = "ends_at", nullable = false) private LocalDateTime endsAt;
    @Column(nullable = false) private Integer capacity;
    @Column(name = "trainer_user_gym_id") private Long trainerUserGymId;
    @Column(nullable = false, length = 20) private String status;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;
}
