package com.conovax.fitflow.infrastructure.persistence.entities;

import com.conovax.fitflow.domain.entities.GymStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gyms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GymJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gym_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, length = 60)
	private String name;

	@Column(nullable = false, length = 20)
	private String nit;

	@Column(nullable = false)
	private String logo;

	@Column(name = "municipalitie_id", nullable = false)
	private Long municipalitieId;

	@Column(nullable = false)
	private Boolean status;

	@Column(length = 13)
	private String phone;

	@Column(length = 100)
	private String email;

	/** null = sede principal; referencia al gym padre si es sede */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_gym_id")
	private GymJpaEntity parentGym;

	@jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
	@Column(name = "gym_status", columnDefinition = "varchar(20)", nullable = false)
	@Builder.Default
	private GymStatus gymStatus = GymStatus.ACTIVE;

	@Column(name = "trial_expires_at")
	private java.time.LocalDateTime trialExpiresAt;
}
