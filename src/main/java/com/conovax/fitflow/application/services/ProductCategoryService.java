package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.ProductCategoryRequest;
import com.conovax.fitflow.application.dto.response.ProductCategoryResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.pagination.PaginationUtils;
import com.conovax.fitflow.domain.entities.ProductCategory;
import com.conovax.fitflow.domain.exceptions.DuplicateResourceException;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

	private final ProductCategoryRepository repository;

	@Transactional(readOnly = true)
	public List<ProductCategoryResponse> getAll() {
		return repository.findAllByStatusTrue().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public PageResponse<ProductCategoryResponse> getPage(Integer page, Integer size, String name) {
		return PaginationUtils.map(
				repository.findAllByNameContaining(name, PaginationUtils.pageable(page, size)),
				this::toResponse
		);
	}

	@Transactional(readOnly = true)
	public ProductCategoryResponse getById(Long id) {
		ProductCategory entity = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
		return toResponse(entity);
	}

	@Transactional
	public ProductCategoryResponse create(ProductCategoryRequest request) {
		if (repository.existsByNameIgnoreCaseAndStatusTrue(request.name())) {
			throw new DuplicateResourceException("Ya existe una categoría con ese nombre");
		}

		ProductCategory entity = ProductCategory.builder()
				.name(request.name())
				.status(true)
				.build();
		return toResponse(repository.save(entity));
	}

	@Transactional
	public ProductCategoryResponse update(Long id, ProductCategoryRequest request) {
		ProductCategory entity = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));

		if (repository.existsByNameIgnoreCaseAndIdNotAndStatusTrue(request.name(), id)) {
			throw new DuplicateResourceException("Ya existe una categoría con ese nombre");
		}

		ProductCategory updated = entity.toBuilder().name(request.name()).build();
		return toResponse(repository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long id) {
		ProductCategory entity = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
		repository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		ProductCategory entity = repository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
		repository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Categoría no encontrada con ID: " + id);
		}
		repository.deleteById(id);
	}

	private ProductCategoryResponse toResponse(ProductCategory entity) {
		return new ProductCategoryResponse(entity.getId(), entity.getName(), entity.getStatus());
	}
}
