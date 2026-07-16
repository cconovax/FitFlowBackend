package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.ProductCategoryJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategoryJpaEntity, Long> {

	List<ProductCategoryJpaEntity> findAllByStatusTrue();

	Page<ProductCategoryJpaEntity> findAllByStatusTrueAndNameContainingIgnoreCase(String name, Pageable pageable);

	Page<ProductCategoryJpaEntity> findAllByStatusFalseAndNameContainingIgnoreCase(String name, Pageable pageable);

	Page<ProductCategoryJpaEntity> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

	Optional<ProductCategoryJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<ProductCategoryJpaEntity> findByIdAndStatusFalse(Long id);

	boolean existsByNameIgnoreCaseAndStatusTrue(String name);

	boolean existsByNameIgnoreCaseAndIdNotAndStatusTrue(String name, Long id);
}
