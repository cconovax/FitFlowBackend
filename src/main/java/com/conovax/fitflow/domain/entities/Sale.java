package com.conovax.fitflow.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Sale {
	private Long id;
	private Long gymId;
	private Long sellerId;
	private LocalDateTime saleDate;
	private BigDecimal subtotal;
	private BigDecimal total;
	private String paymentMethod;
	private BigDecimal paidWith;
	private BigDecimal changeAmount;
	private String notes;
	private Boolean status;
	private List<SaleDetail> details;
}
