package com.conovax.sexbody.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(
		@NotNull(message = "El gymId es obligatorio")
		Long gymId,

		@NotBlank(message = "El código de barras es obligatorio")
		String barcode,

		@NotBlank(message = "El nombre es obligatorio")
		String name,

		@NotBlank(message = "La descripción es obligatoria")
		String description,

		@NotNull(message = "La categoría es obligatoria")
		Long productsCategirieId,

		@NotNull(message = "El precio de venta es obligatorio")
		@PositiveOrZero(message = "El precio de venta debe ser mayor o igual a 0")
		BigDecimal salePraci,

		@NotNull(message = "El precio de compra es obligatorio")
		@PositiveOrZero(message = "El precio de compra debe ser mayor o igual a 0")
		BigDecimal buyPraci,

		@NotNull(message = "El stock actual es obligatorio")
		@PositiveOrZero(message = "El stock actual debe ser mayor o igual a 0")
		Integer currentStock,

		@NotNull(message = "El stock mínimo es obligatorio")
		@PositiveOrZero(message = "El stock mínimo debe ser mayor o igual a 0")
		Integer minStock,

		@NotNull(message = "La moneda es obligatoria")
		@Positive(message = "La moneda debe ser válida")
		Long currencieId
) {
}
