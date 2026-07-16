package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.ProductJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.projections.ProductInventoryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {

	List<ProductJpaEntity> findAllByStatusTrue();

	List<ProductJpaEntity> findAllByGymIdAndStatusTrue(Long gymId);

	Page<ProductJpaEntity> findAllByGymIdAndStatusTrue(Long gymId, Pageable pageable);

	@Query(
			"""
			SELECT p
			FROM ProductJpaEntity p
			WHERE p.gymId = :gymId
				AND (
					:search IS NULL OR :search = ''
					OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
					OR LOWER(p.barcode) LIKE LOWER(CONCAT('%', :search, '%'))
					OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))
				)
			"""
	)
	Page<ProductJpaEntity> findPageByGymIdAndSearch(
			@Param("gymId") Long gymId,
			@Param("search") String search,
			Pageable pageable
	);

	@Query(
			"""
			SELECT p
			FROM ProductJpaEntity p
			WHERE p.gymId = :gymId
				AND p.status = true
				AND (
					:search IS NULL OR :search = ''
					OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
					OR LOWER(p.barcode) LIKE LOWER(CONCAT('%', :search, '%'))
					OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))
				)
			"""
	)
	Page<ProductJpaEntity> findPageByGymIdAndSearchAndStatusTrue(
			@Param("gymId") Long gymId,
			@Param("search") String search,
			Pageable pageable
	);

	Optional<ProductJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<ProductJpaEntity> findByIdAndGymIdAndStatusTrue(Long id, Long gymId);

	Optional<ProductJpaEntity> findByIdAndStatusFalse(Long id);

	Optional<ProductJpaEntity> findByIdAndGymId(Long id, Long gymId);

	Optional<ProductJpaEntity> findByIdAndGymIdAndStatusFalse(Long id, Long gymId);

	List<ProductJpaEntity> findByNameContainingIgnoreCaseAndStatusTrue(String name);

	List<ProductJpaEntity> findByGymIdAndNameContainingIgnoreCaseAndStatusTrue(Long gymId, String name);

	boolean existsByBarcodeIgnoreCaseAndGymIdAndStatusTrue(String barcode, Long gymId);

	boolean existsByBarcodeIgnoreCaseAndGymIdAndIdNotAndStatusTrue(String barcode, Long gymId, Long id);

	Optional<ProductJpaEntity> findByBarcodeIgnoreCaseAndGymIdAndStatusTrue(String barcode, Long gymId);

	@Query(
			nativeQuery = true,
			value = """
				SELECT
				    p.product_id      AS id,
				    p.name            AS name,
				    p.barcode         AS barcode,
				    p.sale_praci      AS salepraci,
				    p.current_stock   AS currentstock,
				    COALESCE(SUM(sd.amount), 0)   AS unitssold,
				    COALESCE(SUM(sd.subtotal), 0) AS revenue,
				    MAX(s.sale_date)               AS lastsaledate
				FROM products p
				LEFT JOIN sale_details sd ON sd.product_id = p.product_id
				LEFT JOIN sales s
				    ON s.sale_id = sd.sale_id
				    AND s.gym_id = :gymId
				    AND s.status = true
				    AND s.sale_date >= :fromDt
				    AND s.sale_date <= :toDt
				WHERE p.gym_id = :gymId AND p.status = true
				GROUP BY p.product_id, p.name, p.barcode, p.sale_praci, p.current_stock
				ORDER BY p.name ASC
				"""
	)
	List<ProductInventoryProjection> findInventoryByGymId(
			@Param("gymId") Long gymId,
			@Param("fromDt") LocalDateTime fromDt,
			@Param("toDt") LocalDateTime toDt
	);

}
