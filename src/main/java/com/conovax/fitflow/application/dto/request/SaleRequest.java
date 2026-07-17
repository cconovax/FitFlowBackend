package com.conovax.fitflow.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SaleRequest(

		@NotNull(message = "El gymId es obligatorio")
		Long gymId,

		Long sellerId,

		@NotBlank(message = "El método de pago es obligatorio")
		String paymentMethod,

	java.math.BigDecimal paidWith,

	String notes,

	@NotEmpty(message = "La venta debe tener al menos un producto")
	List<SaleItemRequest> items
) {}
