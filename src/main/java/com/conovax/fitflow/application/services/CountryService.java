package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.CountryRequest;
import com.conovax.sexbody.application.dto.response.CountryResponse;
import com.conovax.sexbody.application.pagination.PageResponse;
import com.conovax.sexbody.application.pagination.PaginationUtils;
import com.conovax.sexbody.domain.entities.Country;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

	private final CountryRepository countryRepository;

	@Transactional(readOnly = true)
	public List<CountryResponse> getAll() {
		return countryRepository.findAllByStatusTrue().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public PageResponse<CountryResponse> getAll(Integer page, Integer size, String name) {
		Pageable pageable = PaginationUtils.pageable(page, size);

		Page<Country> countries;
		if (name == null || name.isBlank()) {
			countries = countryRepository.findAllByStatusTrue(pageable);
		} else {
			countries = countryRepository.findAllByStatusTrueAndNameContainingIgnoreCase(name.trim(), pageable);
		}

		return PaginationUtils.map(countries, this::toResponse);
	}

	@Transactional(readOnly = true)
	public CountryResponse getById(Long id) {
		Country entity = countryRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + id));
		return toResponse(entity);
	}

	@Transactional(readOnly = true)
	public List<CountryResponse> searchByName(String name) {
		return countryRepository.findByNameContainingIgnoreCaseAndStatusTrue(name)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional
	public CountryResponse create(CountryRequest request) {
		Country entity = Country.builder()
				.name(request.name())
				.status(true)
				.build();
		return toResponse(countryRepository.save(entity));
	}

	@Transactional
	public CountryResponse update(Long id, CountryRequest request) {
		Country entity = countryRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + id));
		Country updated = entity.toBuilder().name(request.name()).build();
		return toResponse(countryRepository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long id) {
		Country entity = countryRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + id));
		countryRepository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		Country entity = countryRepository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + id));
		countryRepository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!countryRepository.existsById(id)) {
			throw new ResourceNotFoundException("País no encontrado con ID: " + id);
		}
		countryRepository.deleteById(id);
	}

	private CountryResponse toResponse(Country entity) {
		return new CountryResponse(entity.getId(), entity.getName(), entity.getStatus());
	}
}
