package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.ProductRequest;
import com.conovax.sexbody.application.dto.response.ProductInventoryResponse;
import com.conovax.sexbody.application.dto.response.ProductResponse;
import com.conovax.sexbody.application.pagination.PageResponse;
import com.conovax.sexbody.application.pagination.PaginationUtils;
import com.conovax.sexbody.domain.entities.Product;
import com.conovax.sexbody.domain.exceptions.DuplicateResourceException;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository repository;

	@Transactional(readOnly = true)
	public PageResponse<ProductResponse> getAll(Long gymId, Integer page, Integer size, String search) {
		Pageable pageable = PaginationUtils.pageable(page, size);
		Page<Product> products = repository.findPageByGymIdAndSearch(gymId, search, pageable);
		return PaginationUtils.map(products, this::toResponse);
	}

	@Transactional(readOnly = true)
 	public ProductResponse getById(Long gymId, Long id) {
		Product entity = repository.findByIdAndGymIdAndStatusTrue(id, gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
		return toResponse(entity);
	}

	@Transactional(readOnly = true)
	public List<ProductResponse> search(String name, Long gymId) {
		return repository.findByGymIdAndNameContainingIgnoreCaseAndStatusTrue(gymId, name).stream().map(this::toResponse).toList();
	}

	/**
	 * Búsqueda rápida para POS: coincidencia exacta de barcode primero,
	 * luego búsqueda por nombre/descripción (máx. 10 resultados).
	 */
	@Transactional(readOnly = true)
	public List<ProductResponse> lookup(Long gymId, String q) {
		if (q == null || q.isBlank()) return List.of();

		// 1. Coincidencia exacta de barcode
		java.util.Optional<ProductResponse> exact = repository
				.findByBarcodeIgnoreCaseAndGymIdAndStatusTrue(q.trim(), gymId)
				.map(this::toResponse);
		if (exact.isPresent()) return List.of(exact.get());

		// 2. Búsqueda por nombre / descripción (paginated, toma primeros 10)
		Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 10);
		return repository.findPageByGymIdAndSearchAndStatusTrue(gymId, q.trim(), pageable)
				.stream().map(this::toResponse).toList();
	}

	@Transactional
	public ProductResponse create(Long gymId, ProductRequest request) {
		if (!gymId.equals(request.gymId())) {
			throw new IllegalArgumentException("El gymId de la URL no coincide con el gymId del body");
		}

		if (repository.existsByBarcodeIgnoreCaseAndGymIdAndStatusTrue(request.barcode(), gymId)) {
			throw new DuplicateResourceException("Ya existe un producto con ese barcode en el gym");
		}

		LocalDate now = LocalDate.now();
		Product entity = Product.builder()
				.gymId(gymId)
				.barcode(request.barcode())
				.name(request.name())
				.description(request.description())
				.productsCategirieId(request.productsCategirieId())
				.salePraci(request.salePraci())
				.buyPraci(request.buyPraci())
				.currentStock(request.currentStock())
				.minStock(request.minStock())
				.currencieId(request.currencieId())
				.createdAt(now)
				.updatedAt(now)
				.status(true)
				.build();

		return toResponse(repository.save(entity));
	}

	@Transactional
 	public ProductResponse update(Long gymId, Long id, ProductRequest request) {
		if (!gymId.equals(request.gymId())) {
			throw new IllegalArgumentException("El gymId de la URL no coincide con el gymId del body");
		}

		Product entity = repository.findByIdAndGymIdAndStatusTrue(id, gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

		if (repository.existsByBarcodeIgnoreCaseAndGymIdAndIdNotAndStatusTrue(request.barcode(), gymId, id)) {
			throw new DuplicateResourceException("Ya existe un producto con ese barcode en el gym");
		}

		Product updated = entity.toBuilder()
				.gymId(gymId)
				.barcode(request.barcode())
				.name(request.name())
				.description(request.description())
				.productsCategirieId(request.productsCategirieId())
				.salePraci(request.salePraci())
				.buyPraci(request.buyPraci())
				.currentStock(request.currentStock())
				.minStock(request.minStock())
				.currencieId(request.currencieId())
				.updatedAt(LocalDate.now())
				.build();

		return toResponse(repository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long gymId, Long id) {
		Product entity = repository.findByIdAndGymIdAndStatusTrue(id, gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
		repository.save(entity.toBuilder()
				.status(false)
				.updatedAt(LocalDate.now())
				.build());
	}

	@Transactional
	public void reset(Long gymId, Long id) {
		Product entity = repository.findByIdAndGymIdAndStatusFalse(id, gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
		repository.save(entity.toBuilder()
				.status(true)
				.updatedAt(LocalDate.now())
				.build());
	}

	@Transactional
	public void forceDelete(Long gymId, Long id) {
		Product entity = repository.findByIdAndGymId(id, gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
		repository.deleteById(entity.getId());
	}

	@Transactional(readOnly = true)
	public List<ProductInventoryResponse> getInventory(Long gymId, String from, String to) {
		LocalDateTime fromDt = (from != null && !from.isBlank())
				? LocalDateTime.parse(from + "T00:00:00")
				: LocalDateTime.of(1900, 1, 1, 0, 0, 0);
		LocalDateTime toDt = (to != null && !to.isBlank())
				? LocalDateTime.parse(to + "T23:59:59")
				: LocalDateTime.now().plusYears(100);

		return repository.findInventory(gymId, fromDt, toDt).stream()
				.map(item -> new ProductInventoryResponse(
						item.id(),
						item.name(),
						item.barcode(),
						item.salePraci(),
						item.currentStock(),
						item.unitsSold(),
						item.revenue(),
						item.lastSaleDate()
				))
				.toList();
	}

	private ProductResponse toResponse(Product entity) {
		return new ProductResponse(
				entity.getId(),
				entity.getGymId(),
				entity.getBarcode(),
				entity.getName(),
				entity.getDescription(),
				entity.getProductsCategirieId(),
				entity.getSalePraci(),
				entity.getBuyPraci(),
				entity.getCurrentStock(),
				entity.getMinStock(),
				entity.getCurrencieId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getStatus()
		);
	}
}
