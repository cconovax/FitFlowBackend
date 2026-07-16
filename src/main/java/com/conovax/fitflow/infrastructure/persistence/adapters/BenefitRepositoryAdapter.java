package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.Benefit;
import com.conovax.sexbody.domain.repositories.BenefitRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.BenefitJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.BenefitMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.BenefitJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BenefitRepositoryAdapter implements BenefitRepository {

	private final BenefitJpaRepository jpaRepository;
	private final BenefitMapper mapper;

	public BenefitRepositoryAdapter(BenefitJpaRepository jpaRepository, BenefitMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Benefit save(Benefit benefit) {
		BenefitJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(benefit));
		return mapper.toDomain(saved);
	}

	@Override
	public List<Benefit> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Benefit> findAllByIdInAndStatusTrue(List<Long> ids) {
		return jpaRepository.findAllByIdInAndStatusTrue(ids).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Benefit> findAllActiveByGymIdOrGlobal(Long gymId) {
		return jpaRepository.findAllActiveByGymIdOrGlobal(gymId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Benefit> findAllActiveByGymIdOrGlobalAndSearchFilter(Long gymId, String search, Pageable pageable) {
		return jpaRepository.findAllActiveByGymIdOrGlobalAndSearchFilter(gymId, search, pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Benefit> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Benefit> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public boolean existsByNameIgnoreCaseAndGymIdAndStatusTrue(String name, Long gymId) {
		return jpaRepository.existsByNameIgnoreCaseAndGymIdAndStatusTrue(name, gymId);
	}

	@Override
	public boolean existsByNameIgnoreCaseAndGymIdAndIdNotAndStatusTrue(String name, Long gymId, Long id) {
		return jpaRepository.existsByNameIgnoreCaseAndGymIdAndIdNotAndStatusTrue(name, gymId, id);
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
