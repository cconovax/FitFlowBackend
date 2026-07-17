package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "gym_id", nullable = false)
	private Long gymId;

	@Column(nullable = false, length = 100)
	private String barcode;

	@Column(nullable = false, length = 60)
	private String name;

	@Column(nullable = false, length = 150)
	private String description;

	@Column(name = "products_categirie_id", nullable = false)
	private Long productsCategirieId;

	@Column(name = "sale_praci", nullable = false, precision = 10, scale = 0)
	private BigDecimal salePraci;

	@Column(name = "buy_praci", nullable = false, precision = 10, scale = 0)
	private BigDecimal buyPraci;

	@Column(name = "current_stock", nullable = false)
	private Integer currentStock;

	@Column(name = "min_stock", nullable = false)
	private Integer minStock;

	@Column(name = "currencie_id", nullable = false)
	private Long currencieId;

	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDate updatedAt;

	@Column
	private Boolean status;
}
