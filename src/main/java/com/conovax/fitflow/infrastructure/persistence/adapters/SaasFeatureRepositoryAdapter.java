package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.SaasFeature;
import com.conovax.sexbody.domain.repositories.SaasFeatureRepository;
import com.conovax.sexbody.infrastructure.persistence.mappers.SaasFeatureMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.SaasFeatureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SaasFeatureRepositoryAdapter implements SaasFeatureRepository {

	private final SaasFeatureJpaRepository jpaRepository;
	private final SaasFeatureMapper mapper;

	@Override
	public SaasFeature save(SaasFeature feature) {
		return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(feature)));
	}

	@Override
	public List<SaasFeature> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public List<SaasFeature> findAll() {
		return jpaRepository.findAll().stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public List<SaasFeature> findAllById(Iterable<Long> ids) {
		return jpaRepository.findAllById(ids).stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public Optional<SaasFeature> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<SaasFeature> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<SaasFeature> findById(Long id) {
		return jpaRepository.findById(id)
				.map(mapper::toDomain);
	}

	@Override
	public boolean existsByCodeIgnoreCase(String code) {
		return jpaRepository.existsByCodeIgnoreCase(code);
	}

	@Override
	public boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id) {
		return jpaRepository.existsByCodeIgnoreCaseAndIdNot(code, id);
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