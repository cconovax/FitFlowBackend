package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "membership_ratings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MembershipRatingJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "membership_rating_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "user_gym_membership_id", nullable = false)
	private Long userGymMembershipId;

	@Column(name = "coach_id", nullable = false)
	private Long coachId;

	@Column(nullable = false)
	private LocalDate date;

	@Column(nullable = false, precision = 6, scale = 0)
	private BigDecimal weight;

	@Column(nullable = false, length = 200)
	private String observation;

	@Column(name = "porcentage_fat", nullable = false, precision = 3, scale = 0)
	private BigDecimal porcentageFat;

	@Column(name = "muscle_mass", nullable = false, precision = 3, scale = 0)
	private BigDecimal muscleMass;

	@Column
	private Boolean status;
}
