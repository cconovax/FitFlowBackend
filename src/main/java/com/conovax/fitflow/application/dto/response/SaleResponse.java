package com.conovax.fitflow.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResponse(
		Long id,
		Long gymId,
		Long sellerId,
		String sellerName,
		LocalDateTime saleDate,
		BigDecimal subtotal,
		BigDecimal total,
		String paymentMethod,
		BigDecimal paidWith,
		BigDecimal changeAmount,
		String notes,
		Boolean status,
		List<SaleDetailResponse> details
) {}
