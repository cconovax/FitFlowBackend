package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.MembershipResult;
import com.conovax.sexbody.domain.repositories.MembershipResultRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.MembershipResultJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.MembershipResultMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.MembershipResultJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MembershipResultRepositoryAdapter implements MembershipResultRepository {

	private final MembershipResultJpaRepository jpaRepository;
	private final MembershipResultMapper mapper;

	public MembershipResultRepositoryAdapter(MembershipResultJpaRepository jpaRepository, MembershipResultMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public MembershipResult save(MembershipResult entity) {
		MembershipResultJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(entity));
		return mapper.toDomain(saved);
	}

	@Override
	public Optional<MembershipResult> findByUserGymMembershipId(Long userGymMembershipId) {
		return jpaRepository.findByUserGymMembershipId(userGymMembershipId)
				.map(mapper::toDomain);
	}

	@Override
	public boolean existsByUserGymMembershipId(Long userGymMembershipId) {
		return jpaRepository.existsByUserGymMembershipId(userGymMembershipId);
	}
}
