package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository {
	ProductCategory save(ProductCategory category);

	List<ProductCategory> findAllByStatusTrue();

	Page<ProductCategory> findAllByStatusTrueAndNameContaining(String name, Pageable pageable);

	Page<ProductCategory> findAllByStatusFalseAndNameContaining(String name, Pageable pageable);

	Page<ProductCategory> findAllByNameContaining(String name, Pageable pageable);

	Optional<ProductCategory> findByIdAndStatusTrue(Long id);

	Optional<ProductCategory> findByIdAndStatusFalse(Long id);

	boolean existsByNameIgnoreCaseAndStatusTrue(String name);

	boolean existsByNameIgnoreCaseAndIdNotAndStatusTrue(String name, Long id);

	boolean existsById(Long id);

	void deleteById(Long id);
}
