package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.SaleDetail;
import com.conovax.sexbody.domain.repositories.SaleDetailRepository;
import com.conovax.sexbody.infrastructure.persistence.mappers.SaleDetailMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.SaleDetailJpaRepository;
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
