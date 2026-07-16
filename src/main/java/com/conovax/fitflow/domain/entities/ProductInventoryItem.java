package com.conovax.sexbody.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductInventoryItem(
		Long id,
		String name,
		String barcode,
		BigDecimal salePraci,
		Integer currentStock,
		Long unitsSold,
		BigDecimal revenue,
		LocalDateTime lastSaleDate
) {}
