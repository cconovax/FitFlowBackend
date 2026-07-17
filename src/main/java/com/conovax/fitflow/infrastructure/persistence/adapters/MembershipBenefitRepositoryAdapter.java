package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.MembershipBenefit;
import com.conovax.fitflow.domain.repositories.MembershipBenefitRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.MembershipBenefitJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.MembershipBenefitMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.MembershipBenefitJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MembershipBenefitRepositoryAdapter implements MembershipBenefitRepository {

	private final MembershipBenefitJpaRepository jpaRepository;
	private final MembershipBenefitMapper mapper;

	public MembershipBenefitRepositoryAdapter(MembershipBenefitJpaRepository jpaRepository, MembershipBenefitMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public void deleteByMembershipId(Long membershipId) {
		jpaRepository.deleteByMembershipId(membershipId);
	}

	@Override
	public List<MembershipBenefit> findAllByMembershipIdIn(Collection<Long> membershipIds) {
		return jpaRepository.findAllByMembershipIdIn(membershipIds).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<MembershipBenefit> saveAll(List<MembershipBenefit> entities) {
		List<MembershipBenefitJpaEntity> jpaEntities = entities.stream()
				.map(mapper::toJpaEntity)
				.collect(Collectors.toList());

		return jpaRepository.saveAll(jpaEntities).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}
}
