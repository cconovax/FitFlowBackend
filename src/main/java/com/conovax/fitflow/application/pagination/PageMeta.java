package com.conovax.fitflow.application.pagination;

public record PageMeta(
		int page,
		int size,
		long totalElements,
		int totalPages,
		boolean first,
		boolean last
) {
}
