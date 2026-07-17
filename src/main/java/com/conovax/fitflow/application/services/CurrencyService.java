package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.CurrencyRequest;
import com.conovax.fitflow.application.dto.response.CurrencyResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.pagination.PaginationUtils;
import com.conovax.fitflow.domain.entities.Currency;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrencyService {

	private final CurrencyRepository currencyRepository;

	@Transactional(readOnly = true)
	public PageResponse<CurrencyResponse> getAll(Integer page, Integer size, String search) {
		Pageable pageable = PaginationUtils.pageable(page, size);
		Page<Currency> currencies = currencyRepository.findAllWithSearchFilter(search, pageable);
		return PaginationUtils.map(currencies, this::toResponse);
	}

	@Transactional(readOnly = true)
	public CurrencyResponse getById(Long id) {
		Currency entity = currencyRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Moneda no encontrada con ID: " + id));
		return toResponse(entity);
	}

	@Transactional
	public CurrencyResponse create(CurrencyRequest request) {
		Currency entity = Currency.builder()
				.name(request.name())
				.code(request.code())
				.status(true)
				.build();
		return toResponse(currencyRepository.save(entity));
	}

	@Transactional
	public CurrencyResponse update(Long id, CurrencyRequest request) {
		Currency entity = currencyRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Moneda no encontrada con ID: " + id));
		Currency updated = entity.toBuilder()
				.name(request.name())
				.code(request.code())
				.build();
		return toResponse(currencyRepository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long id) {
		Currency entity = currencyRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Moneda no encontrada con ID: " + id));
		currencyRepository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		Currency entity = currencyRepository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Moneda no encontrada con ID: " + id));
		currencyRepository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!currencyRepository.existsById(id)) {
			throw new ResourceNotFoundException("Moneda no encontrada con ID: " + id);
		}
		currencyRepository.deleteById(id);
	}

	private CurrencyResponse toResponse(Currency entity) {
		return new CurrencyResponse(entity.getId(), entity.getName(), entity.getCode(), entity.getStatus());
	}
}