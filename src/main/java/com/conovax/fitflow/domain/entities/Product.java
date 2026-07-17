package com.conovax.fitflow.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {
	private Long id;
	private Long gymId;
	private String barcode;
	private String name;
	private String description;
	private Long productsCategirieId;
	private BigDecimal salePraci;
	private BigDecimal buyPraci;
	private Integer currentStock;
	private Integer minStock;
	private Long currencieId;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	private Boolean status;
}
