package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.MembershipCoach;
import com.conovax.sexbody.domain.repositories.MembershipCoachRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.MembershipCoachJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.MembershipCoachMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.MembershipCoachJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MembershipCoachRepositoryAdapter implements MembershipCoachRepository {

	private final MembershipCoachJpaRepository jpaRepository;
	private final MembershipCoachMapper mapper;

	public MembershipCoachRepositoryAdapter(MembershipCoachJpaRepository jpaRepository, MembershipCoachMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public MembershipCoach save(MembershipCoach entity) {
		MembershipCoachJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(entity));
		return mapper.toDomain(saved);
	}

	@Override
	public Optional<MembershipCoach> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<MembershipCoach> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public List<MembershipCoach> findAllByGymId(Long gymId) {
		return jpaRepository.findAllByGymId(gymId).stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public List<MembershipCoach> findAllByCoachId(Long coachId) {
		return jpaRepository.findAllByCoachId(coachId).stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public boolean existsByMembershipIdAndCoachIdAndStatusTrue(Long membershipId, Long coachId) {
		return jpaRepository.existsByMembershipIdAndCoachIdAndStatusTrue(membershipId, coachId);
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
