package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.MembershipJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipJpaRepository extends JpaRepository<MembershipJpaEntity, Long> {
	List<MembershipJpaEntity> findAllByStatusTrue();

	List<MembershipJpaEntity> findAllByGymIdAndStatusTrue(Long gymId);

	List<MembershipJpaEntity> findAllByGymIdOrGymIdIsNullAndStatusTrue(Long gymId);

	Optional<MembershipJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<MembershipJpaEntity> findByIdAndStatusFalse(Long id);

	boolean existsByNameIgnoreCaseAndGymIdAndStatusTrue(String name, Long gymId);

	boolean existsByNameIgnoreCaseAndGymIdAndIdNotAndStatusTrue(String name, Long gymId, Long id);
}
