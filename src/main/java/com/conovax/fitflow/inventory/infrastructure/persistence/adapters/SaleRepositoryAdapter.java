package com.conovax.fitflow.inventory.infrastructure.persistence.adapters;

import com.conovax.fitflow.inventory.domain.entities.Sale;
import com.conovax.fitflow.inventory.domain.repositories.SaleRepository;
import com.conovax.fitflow.inventory.infrastructure.persistence.mappers.SaleMapper;
import com.conovax.fitflow.inventory.infrastructure.persistence.repositories.SaleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SaleRepositoryAdapter implements SaleRepository {

	private final SaleJpaRepository jpaRepository;
	private final SaleMapper mapper;

	@Override
	public Sale save(Sale sale) {
		return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(sale)));
	}

	@Override
	public Optional<Sale> findById(Long id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public Page<Sale> findAllByGymIdAndStatusTrue(Long gymId, Pageable pageable) {
		return jpaRepository.findAllByGymIdAndStatusTrue(gymId, pageable).map(mapper::toDomain);
	}

	@Override
	public Page<Sale> findByGymIdAndDateRange(Long gymId, LocalDateTime from, LocalDateTime to, Pageable pageable) {
		return jpaRepository.findByGymIdAndDateRange(gymId, from, to, pageable).map(mapper::toDomain);
	}

	@Override
	public BigDecimal sumTotalRevenueByGymId(Long gymId) {
		return jpaRepository.sumTotalRevenueByGymId(gymId);
	}

	@Override
	public BigDecimal sumRevenueByGymIdAndDateRange(Long gymId, LocalDateTime from, LocalDateTime to) {
		return jpaRepository.sumRevenueByGymIdAndDateRange(gymId, from, to);
	}

	@Override
	public long countSalesByGymIdAndDateRange(Long gymId, LocalDateTime from, LocalDateTime to) {
		return jpaRepository.countSalesByGymIdAndDateRange(gymId, from, to);
	}

	@Override
	public List<Object[]> findDailySalesByGymId(Long gymId, LocalDateTime from, LocalDateTime to) {
		return jpaRepository.findDailySalesByGymId(gymId, from, to);
	}
}
