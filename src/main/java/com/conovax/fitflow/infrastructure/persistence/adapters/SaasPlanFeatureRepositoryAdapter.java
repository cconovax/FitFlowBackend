package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.SaasPlanFeature;
import com.conovax.sexbody.domain.repositories.SaasPlanFeatureRepository;
import com.conovax.sexbody.infrastructure.persistence.mappers.SaasPlanFeatureMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.SaasPlanFeatureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SaasPlanFeatureRepositoryAdapter implements SaasPlanFeatureRepository {

	private final SaasPlanFeatureJpaRepository jpaRepository;
	private final SaasPlanFeatureMapper mapper;

	@Override
	public SaasPlanFeature save(SaasPlanFeature entity) {
		return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(entity)));
	}

	@Override
	public List<SaasPlanFeature> saveAll(List<SaasPlanFeature> entities) {
		return jpaRepository.saveAll(entities.stream().map(mapper::toJpaEntity).toList()).stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public List<SaasPlanFeature> findAllBySaasPlanId(Long saasPlanId) {
		return jpaRepository.findAllBySaasPlan_Id(saasPlanId).stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public void deleteBySaasPlanId(Long saasPlanId) {
		jpaRepository.deleteBySaasPlan_Id(saasPlanId);
	}
}