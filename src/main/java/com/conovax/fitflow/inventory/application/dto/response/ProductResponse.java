package com.conovax.fitflow.inventory.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductResponse(
		Long id,
		Long gymId,
		String barcode,
		String name,
		String description,
		Long productsCategirieId,
		BigDecimal salePraci,
		BigDecimal buyPraci,
		Integer currentStock,
		Integer minStock,
		Long currencyId,
		LocalDate createdAt,
		LocalDate updatedAt,
		Boolean status
) {
}
