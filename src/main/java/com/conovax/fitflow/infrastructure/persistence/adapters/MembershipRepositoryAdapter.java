package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.Membership;
import com.conovax.sexbody.domain.repositories.MembershipRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.MembershipJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.MembershipMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.MembershipJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MembershipRepositoryAdapter implements MembershipRepository {

	private final MembershipJpaRepository jpaRepository;
	private final MembershipMapper mapper;

	public MembershipRepositoryAdapter(MembershipJpaRepository jpaRepository, MembershipMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Membership save(Membership membership) {
		MembershipJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(membership));
		return mapper.toDomain(saved);
	}

	@Override
	public List<Membership> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Membership> findAllByGymIdAndStatusTrue(Long gymId) {
		return jpaRepository.findAllByGymIdAndStatusTrue(gymId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Membership> findAllByGymIdOrGymIdIsNullAndStatusTrue(Long gymId) {
		return jpaRepository.findAllByGymIdOrGymIdIsNullAndStatusTrue(gymId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Membership> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<Membership> findByIdAndStatusFalse(Long id) {
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
	public List<Membership> findAllById(Iterable<Long> ids) {
		return jpaRepository.findAllById(ids).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
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
