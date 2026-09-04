package com.conovax.fitflow.membership.infrastructure.persistence.adapters;

import com.conovax.fitflow.membership.domain.entities.MembershipResult;
import com.conovax.fitflow.membership.domain.repositories.MembershipResultRepository;
import com.conovax.fitflow.membership.infrastructure.persistence.entities.MembershipResultJpaEntity;
import com.conovax.fitflow.membership.infrastructure.persistence.mappers.MembershipResultMapper;
import com.conovax.fitflow.membership.infrastructure.persistence.repositories.MembershipResultJpaRepository;
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
