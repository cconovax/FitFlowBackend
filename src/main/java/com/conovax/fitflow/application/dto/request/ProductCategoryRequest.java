package com.conovax.sexbody.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProductCategoryRequest(
		@NotBlank(message = "El nombre de la categoría es obligatorio")
		String name
) {
}
