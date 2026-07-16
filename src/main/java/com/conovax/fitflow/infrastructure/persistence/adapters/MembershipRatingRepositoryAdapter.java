package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.MembershipRating;
import com.conovax.sexbody.domain.repositories.MembershipRatingRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.MembershipRatingJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.MembershipRatingMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.MembershipRatingJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MembershipRatingRepositoryAdapter implements MembershipRatingRepository {

	private final MembershipRatingJpaRepository jpaRepository;
	private final MembershipRatingMapper mapper;

	public MembershipRatingRepositoryAdapter(MembershipRatingJpaRepository jpaRepository, MembershipRatingMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public MembershipRating save(MembershipRating entity) {
		MembershipRatingJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(entity));
		return mapper.toDomain(saved);
	}

	@Override
	public List<MembershipRating> findAllByUserGymMembershipIdOrderByDateDescIdDesc(Long userGymMembershipId) {
		return jpaRepository.findAllByUserGymMembershipIdOrderByDateDescIdDesc(userGymMembershipId).stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public Optional<MembershipRating> findOldestByUserGymMembershipId(Long userGymMembershipId) {
		return jpaRepository.findTopByUserGymMembershipIdOrderByDateAscIdAsc(userGymMembershipId)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<MembershipRating> findById(Long id) {
		return jpaRepository.findById(id)
				.map(mapper::toDomain);
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
