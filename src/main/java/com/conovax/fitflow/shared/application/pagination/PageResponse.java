package com.conovax.fitflow.shared.application.pagination;

import java.util.List;

public record PageResponse<T>(
		List<T> content,
		PageMeta meta
) {
}
