package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.SaleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleJpaRepository extends JpaRepository<SaleJpaEntity, Long> {

	Page<SaleJpaEntity> findAllByGymIdAndStatusTrue(Long gymId, Pageable pageable);

	@Query("""
		SELECT s FROM SaleJpaEntity s
		WHERE s.gymId = :gymId
		  AND s.status = true
		  AND s.saleDate >= :from
		  AND s.saleDate <= :to
		ORDER BY s.saleDate DESC
		""")
	Page<SaleJpaEntity> findByGymIdAndDateRange(
			@Param("gymId") Long gymId,
			@Param("from") LocalDateTime from,
			@Param("to") LocalDateTime to,
			Pageable pageable
	);

	@Query("""
		SELECT COALESCE(SUM(s.total), 0)
		FROM SaleJpaEntity s
		WHERE s.gymId = :gymId AND s.status = true
		""")
	BigDecimal sumTotalRevenueByGymId(@Param("gymId") Long gymId);

	@Query("""
		SELECT COALESCE(SUM(s.total), 0)
		FROM SaleJpaEntity s
		WHERE s.gymId = :gymId AND s.status = true
		  AND s.saleDate >= :from AND s.saleDate <= :to
		""")
	BigDecimal sumRevenueByGymIdAndDateRange(
			@Param("gymId") Long gymId,
			@Param("from") LocalDateTime from,
			@Param("to") LocalDateTime to
	);

	@Query("""
		SELECT COUNT(s)
		FROM SaleJpaEntity s
		WHERE s.gymId = :gymId AND s.status = true
		  AND s.saleDate >= :from AND s.saleDate <= :to
		""")
	long countSalesByGymIdAndDateRange(
			@Param("gymId") Long gymId,
			@Param("from") LocalDateTime from,
			@Param("to") LocalDateTime to
	);

	@Query(nativeQuery = true, value = """
		SELECT DATE(s.sale_date) AS day,
		       COUNT(*) AS cnt,
		       COALESCE(SUM(s.total), 0) AS revenue
		FROM sales s
		WHERE s.gym_id = :gymId
		  AND s.status = true
		  AND s.sale_date >= :from
		  AND s.sale_date <= :to
		GROUP BY DATE(s.sale_date)
		ORDER BY DATE(s.sale_date)
		""")
	List<Object[]> findDailySalesByGymId(
			@Param("gymId") Long gymId,
			@Param("from") LocalDateTime from,
			@Param("to") LocalDateTime to
	);
}
