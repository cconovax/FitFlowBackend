package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaleJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sale_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "gym_id", nullable = false)
	private Long gymId;

	@Column(name = "seller_id")
	private Long sellerId;

	@Column(name = "sale_date", nullable = false)
	private LocalDateTime saleDate;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal subtotal;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal total;

	@Column(name = "payment_method", nullable = false, length = 50)
	private String paymentMethod;

	@Column(name = "paid_with", precision = 12, scale = 2)
	private BigDecimal paidWith;

	@Column(name = "change_amount", precision = 12, scale = 2)
	private BigDecimal changeAmount;

	@Column(length = 200)
	private String notes;

	@Column(nullable = false)
	private Boolean status;
}
