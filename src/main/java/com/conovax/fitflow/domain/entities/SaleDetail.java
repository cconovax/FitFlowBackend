package com.conovax.sexbody.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SaleDetail {
	private Long id;
	private Long saleId;
	private Long productId;
	private Integer amount;
	private BigDecimal unitPrice;
	private BigDecimal subtotal;
}
