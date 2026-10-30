package com.conovax.fitflow.scheduling.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "class_scheduling_settings")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ClassSchedulingSettingsJpaEntity {
    @Id @Column(name = "gym_id") private Long gymId;
    @Column(name = "attendance_mark_window_hours", nullable = false) private Integer attendanceMarkWindowHours;
    @Column(name = "waitlist_offer_cutoff_hours", nullable = false) private Integer waitlistOfferCutoffHours;
    @Column(name = "waitlist_offer_expiration_minutes", nullable = false) private Integer waitlistOfferExpirationMinutes;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false) private LocalDateTime updatedAt;
}
