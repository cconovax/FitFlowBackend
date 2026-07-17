package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.SaleDetail;
import com.conovax.fitflow.domain.repositories.SaleDetailRepository;
import com.conovax.fitflow.infrastructure.persistence.mappers.SaleDetailMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.SaleDetailJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SaleDetailRepositoryAdapter implements SaleDetailRepository {

	private final SaleDetailJpaRepository jpaRepository;
	private final SaleDetailMapper mapper;

	@Override
	public SaleDetail save(SaleDetail detail) {
		return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(detail)));
	}

	@Override
	public List<SaleDetail> findAllBySaleId(Long saleId) {
		return jpaRepository.findAllBySaleId(saleId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}
}
