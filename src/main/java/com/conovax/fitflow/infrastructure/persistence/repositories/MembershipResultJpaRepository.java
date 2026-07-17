package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.MembershipResultJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipResultJpaRepository extends JpaRepository<MembershipResultJpaEntity, Long> {
	Optional<MembershipResultJpaEntity> findByUserGymMembershipId(Long userGymMembershipId);

	boolean existsByUserGymMembershipId(Long userGymMembershipId);
}
