package com.conovax.fitflow.application.pagination;

import java.util.List;

public record PageResponse<T>(
		List<T> content,
		PageMeta meta
) {
}
