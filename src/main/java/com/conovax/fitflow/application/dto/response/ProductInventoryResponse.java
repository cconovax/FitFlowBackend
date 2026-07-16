package com.conovax.sexbody.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductInventoryResponse(
		Long id,
		String name,
		String barcode,
		BigDecimal salePraci,
		Integer currentStock,
		Long unitsSold,
		BigDecimal revenue,
		LocalDateTime lastSaleDate
) {}
