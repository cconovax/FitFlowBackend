package com.conovax.fitflow.scheduling.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "class_purchases")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ClassPurchaseJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id") private Long id;
    @Column(name = "gym_id", nullable = false) private Long gymId;
    @Column(name = "session_id", nullable = false) private Long sessionId;
    @Column(name = "user_gym_id", nullable = false) private Long userGymId;
    @Column(name = "reservation_id") private Long reservationId;
    @Column(name = "sale_id") private Long saleId;
    @Column(name = "seller_user_gym_id") private Long sellerUserGymId;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal amount;
    @Column(name = "payment_method", nullable = false, length = 50) private String paymentMethod;
    @Column(nullable = false, length = 20) private String status;
    @Column(nullable = false, length = 20) private String source;
    @Column(name = "purchased_at", nullable = false) private LocalDateTime purchasedAt;
    @Column(length = 500) private String notes;
}
