package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.Product;
import com.conovax.fitflow.domain.entities.ProductInventoryItem;
import com.conovax.fitflow.domain.repositories.ProductRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.ProductJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.ProductMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.ProductJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryAdapter implements ProductRepository {

	private final ProductJpaRepository jpaRepository;
	private final ProductMapper mapper;

	public ProductRepositoryAdapter(ProductJpaRepository jpaRepository, ProductMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Product save(Product product) {
		ProductJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(product));
		return mapper.toDomain(saved);
	}

	@Override
	public List<Product> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Product> findAllByGymIdAndStatusTrue(Long gymId) {
		return jpaRepository.findAllByGymIdAndStatusTrue(gymId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Product> findAllByGymIdAndStatusTrue(Long gymId, Pageable pageable) {
		return jpaRepository.findAllByGymIdAndStatusTrue(gymId, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Product> findPageByGymIdAndSearch(Long gymId, String search, Pageable pageable) {
		return jpaRepository.findPageByGymIdAndSearch(gymId, search, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<Product> findPageByGymIdAndSearchAndStatusTrue(Long gymId, String search, Pageable pageable) {
		return jpaRepository.findPageByGymIdAndSearchAndStatusTrue(gymId, search, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Product> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Product> findByIdAndGymIdAndStatusTrue(Long id, Long gymId) {
		return jpaRepository.findByIdAndGymIdAndStatusTrue(id, gymId)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Product> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Product> findByIdAndGymId(Long id, Long gymId) {
		return jpaRepository.findByIdAndGymId(id, gymId)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Product> findByIdAndGymIdAndStatusFalse(Long id, Long gymId) {
		return jpaRepository.findByIdAndGymIdAndStatusFalse(id, gymId)
				.map(mapper::toDomain);
	}

	@Override
	public List<Product> findByNameContainingIgnoreCaseAndStatusTrue(String name) {
		return jpaRepository.findByNameContainingIgnoreCaseAndStatusTrue(name).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Product> findByGymIdAndNameContainingIgnoreCaseAndStatusTrue(Long gymId, String name) {
		return jpaRepository.findByGymIdAndNameContainingIgnoreCaseAndStatusTrue(gymId, name).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public boolean existsByBarcodeIgnoreCaseAndGymIdAndStatusTrue(String barcode, Long gymId) {
		return jpaRepository.existsByBarcodeIgnoreCaseAndGymIdAndStatusTrue(barcode, gymId);
	}

	@Override
	public boolean existsByBarcodeIgnoreCaseAndGymIdAndIdNotAndStatusTrue(String barcode, Long gymId, Long id) {
		return jpaRepository.existsByBarcodeIgnoreCaseAndGymIdAndIdNotAndStatusTrue(barcode, gymId, id);
	}

	@Override
	public java.util.Optional<Product> findByBarcodeIgnoreCaseAndGymIdAndStatusTrue(String barcode, Long gymId) {
		return jpaRepository.findByBarcodeIgnoreCaseAndGymIdAndStatusTrue(barcode, gymId)
				.map(mapper::toDomain);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public List<ProductInventoryItem> findInventory(Long gymId, LocalDateTime from, LocalDateTime to) {
		return jpaRepository.findInventoryByGymId(gymId, from, to).stream()
				.map(p -> new ProductInventoryItem(
						p.getId(),
						p.getName(),
						p.getBarcode(),
						p.getSalepraci(),
						p.getCurrentstock(),
						p.getUnitssold(),
						p.getRevenue(),
						p.getLastsaledate()
				))
				.collect(Collectors.toList());
	}
}
