package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.MembershipPrice;
import com.conovax.fitflow.domain.repositories.MembershipPriceRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.MembershipPriceJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.MembershipPriceMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.MembershipPriceJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MembershipPriceRepositoryAdapter implements MembershipPriceRepository {

	private final MembershipPriceJpaRepository jpaRepository;
	private final MembershipPriceMapper mapper;

	public MembershipPriceRepositoryAdapter(MembershipPriceJpaRepository jpaRepository, MembershipPriceMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public List<MembershipPrice> findAllByMembershipIdAndEndDateIsNull(Long membershipId) {
		return jpaRepository.findAllByMembershipIdAndEndDateIsNull(membershipId).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<MembershipPrice> findAllByMembershipIdInAndEndDateIsNull(Collection<Long> membershipIds) {
		return jpaRepository.findAllByMembershipIdInAndEndDateIsNull(membershipIds).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<MembershipPrice> saveAll(List<MembershipPrice> entities) {
		List<MembershipPriceJpaEntity> jpaEntities = entities.stream()
				.map(mapper::toJpaEntity)
				.collect(Collectors.toList());

		return jpaRepository.saveAll(jpaEntities).stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public MembershipPrice save(MembershipPrice entity) {
		MembershipPriceJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(entity));
		return mapper.toDomain(saved);
	}
}
