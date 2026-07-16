package com.conovax.sexbody.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "user_gym_membership")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserGymMembershipJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_gym_membership_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "user_gym_id", nullable = false)
	private Long userGymId;

	@Column(name = "membership_id", nullable = false)
	private Long membershipId;

	@Column(name = "payment_preci", nullable = false, precision = 10, scale = 2)
	private BigDecimal paymentPreci;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Column(nullable = false)
	private Boolean status;
}
