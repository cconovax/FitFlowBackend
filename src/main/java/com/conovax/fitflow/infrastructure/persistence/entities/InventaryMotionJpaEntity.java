package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "inventary_motion")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InventaryMotionJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventary_motion_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "product_id", nullable = false)
	private Long productId;

	@Column(name = "type_motion_id", nullable = false)
	private Long typeMotionId;

	@Column(nullable = false)
	private Integer amount;

	@Column(nullable = false, length = 20)
	private String motive;

	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDate updatedAt;
}
