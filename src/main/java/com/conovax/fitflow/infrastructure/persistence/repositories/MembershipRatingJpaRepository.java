package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.MembershipRatingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRatingJpaRepository extends JpaRepository<MembershipRatingJpaEntity, Long> {
	List<MembershipRatingJpaEntity> findAllByUserGymMembershipIdOrderByDateDescIdDesc(Long userGymMembershipId);

	Optional<MembershipRatingJpaEntity> findTopByUserGymMembershipIdOrderByDateAscIdAsc(Long userGymMembershipId);
}
