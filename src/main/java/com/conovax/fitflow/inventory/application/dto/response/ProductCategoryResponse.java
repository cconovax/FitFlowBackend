package com.conovax.fitflow.inventory.application.dto.response;

public record ProductCategoryResponse(
		Long id,
		String name,
		Boolean status
) {
}
