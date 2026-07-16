package com.conovax.sexbody.application.dto.response;

import java.math.BigDecimal;

public record SaleDetailResponse(
		Long id,
		Long productId,
		String productName,
		String productBarcode,
		Integer amount,
		BigDecimal unitPrice,
		BigDecimal subtotal
) {}
