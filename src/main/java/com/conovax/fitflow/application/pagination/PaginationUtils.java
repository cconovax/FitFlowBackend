package com.conovax.sexbody.application.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.function.Function;

public final class PaginationUtils {

	private PaginationUtils() {
	}

	public static Pageable pageable(Integer page, Integer size) {
		int safePage = (page == null || page < PaginationConfig.DEFAULT_PAGE)
				? PaginationConfig.DEFAULT_PAGE
				: page;
		int safeSize;
		if (size == null) {
			safeSize = PaginationConfig.DEFAULT_SIZE;
		} else if (size <= 0) {
			safeSize = PaginationConfig.DEFAULT_SIZE;
		} else {
			safeSize = Math.min(size, PaginationConfig.MAX_SIZE);
		}
		return PageRequest.of(safePage, safeSize);
	}

	public static Pageable pageable(Integer page, Integer size, Sort sort) {
		int safePage = (page == null || page < PaginationConfig.DEFAULT_PAGE)
				? PaginationConfig.DEFAULT_PAGE
				: page;
		int safeSize;
		if (size == null) {
			safeSize = PaginationConfig.DEFAULT_SIZE;
		} else if (size <= 0) {
			safeSize = PaginationConfig.DEFAULT_SIZE;
		} else {
			safeSize = Math.min(size, PaginationConfig.MAX_SIZE);
		}
		return PageRequest.of(safePage, safeSize, sort);
	}

	public static <T, R> PageResponse<R> map(Page<T> page, Function<T, R> mapper) {
		return new PageResponse<>(
				page.getContent().stream().map(mapper).toList(),
				new PageMeta(
						page.getNumber(),
						page.getSize(),
						page.getTotalElements(),
						page.getTotalPages(),
						page.isFirst(),
						page.isLast()
				)
		);
	}
}
