package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.SaasPlan;
import com.conovax.fitflow.domain.repositories.SaasPlanRepository;
import com.conovax.fitflow.infrastructure.persistence.mappers.SaasPlanMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.SaasPlanJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SaasPlanRepositoryAdapter implements SaasPlanRepository {

	private final SaasPlanJpaRepository jpaRepository;
	private final SaasPlanMapper mapper;

	@Override
	public SaasPlan save(SaasPlan plan) {
		return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(plan)));
	}

	@Override
	public List<SaasPlan> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public List<SaasPlan> findAll() {
		return jpaRepository.findAll().stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public Optional<SaasPlan> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<SaasPlan> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<SaasPlan> findById(Long id) {
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