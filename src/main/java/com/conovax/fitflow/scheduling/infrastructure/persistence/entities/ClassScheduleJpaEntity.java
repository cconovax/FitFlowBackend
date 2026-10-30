package com.conovax.fitflow.scheduling.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "class_schedules")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ClassScheduleJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id") private Long id;
    @Column(name = "gym_id", nullable = false) private Long gymId;
    @Column(nullable = false, length = 120) private String name;
    @Column(length = 500) private String description;
    @Column(nullable = false) private Short weekday;
    @Column(name = "start_time", nullable = false) private LocalTime startTime;
    @Column(name = "end_time", nullable = false) private LocalTime endTime;
    @Column(nullable = false) private Integer capacity;
    @Column(name = "trainer_user_gym_id") private Long trainerUserGymId;
    @Column(nullable = false, length = 20) private String status;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false) private LocalDateTime updatedAt;
}
