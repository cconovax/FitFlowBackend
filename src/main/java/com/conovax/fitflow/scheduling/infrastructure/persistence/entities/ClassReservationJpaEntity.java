package com.conovax.fitflow.scheduling.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "class_reservations")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ClassReservationJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id") private Long id;
    @Column(name = "gym_id", nullable = false) private Long gymId;
    @Column(name = "session_id", nullable = false) private Long sessionId;
    @Column(name = "user_gym_id", nullable = false) private Long userGymId;
    @Column(nullable = false, length = 20) private String status;
    @Column(nullable = false, length = 20) private String source;
    @Column(name = "waitlist_position") private Integer waitlistPosition;
    @Column(name = "reserved_at", nullable = false) private LocalDateTime reservedAt;
    @Column(name = "checked_in_at") private LocalDateTime checkedInAt;
    @Column(name = "checked_in_by_user_gym_id") private Long checkedInByUserGymId;
    @Builder.Default @Column(name = "attendance_confirmation", nullable = false, length = 20) private String attendanceConfirmation = "PENDING";
    @Column(name = "attendance_confirmed_at") private LocalDateTime attendanceConfirmedAt;
    @Column(name = "offered_at") private LocalDateTime offeredAt;
    @Column(name = "offer_expires_at") private LocalDateTime offerExpiresAt;
    @Builder.Default @Column(name = "offer_count", nullable = false) private Integer offerCount = 0;
    @Column(name = "offer_result", length = 20) private String offerResult;
}
