package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.Sale;
import com.conovax.sexbody.domain.repositories.SaleRepository;
import com.conovax.sexbody.infrastructure.persistence.mappers.SaleMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.SaleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
}
