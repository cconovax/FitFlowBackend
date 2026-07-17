package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.ProductCategory;
import com.conovax.fitflow.domain.repositories.ProductCategoryRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.ProductCategoryJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.ProductCategoryMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.ProductCategoryJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductCategoryRepositoryAdapter implements ProductCategoryRepository {

	private final ProductCategoryJpaRepository jpaRepository;
	private final ProductCategoryMapper mapper;

	public ProductCategoryRepositoryAdapter(ProductCategoryJpaRepository jpaRepository, ProductCategoryMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public ProductCategory save(ProductCategory category) {
		ProductCategoryJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(category));
		return mapper.toDomain(saved);
	}

	@Override
	public List<ProductCategory> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<ProductCategory> findAllByStatusTrueAndNameContaining(String name, Pageable pageable) {
		String safeName = name == null ? "" : name;
		return jpaRepository.findAllByStatusTrueAndNameContainingIgnoreCase(safeName, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<ProductCategory> findAllByStatusFalseAndNameContaining(String name, Pageable pageable) {
		String safeName = name == null ? "" : name;
		return jpaRepository.findAllByStatusFalseAndNameContainingIgnoreCase(safeName, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Page<ProductCategory> findAllByNameContaining(String name, Pageable pageable) {
		String safeName = name == null ? "" : name;
		return jpaRepository.findAllByNameContainingIgnoreCase(safeName, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<ProductCategory> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<ProductCategory> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public boolean existsByNameIgnoreCaseAndStatusTrue(String name) {
		return jpaRepository.existsByNameIgnoreCaseAndStatusTrue(name);
	}

	@Override
	public boolean existsByNameIgnoreCaseAndIdNotAndStatusTrue(String name, Long id) {
		return jpaRepository.existsByNameIgnoreCaseAndIdNotAndStatusTrue(name, id);
	}

	@Override
	public boolean existsById(Long id) {
		return jpaRepository.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}
}
