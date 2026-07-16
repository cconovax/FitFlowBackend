package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.RoleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, Long> {

	Optional<RoleJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<RoleJpaEntity> findByNameAndStatusTrue(String name);

	List<RoleJpaEntity> findAllByGymIdAndStatusTrue(Long gymId);

	Page<RoleJpaEntity> findAllByGymIdAndStatusTrue(Long gymId, Pageable pageable);

	List<RoleJpaEntity> findAllByGymIdOrGymIdIsNullAndStatusTrue(Long gymId);

	Page<RoleJpaEntity> findAllByGymIdOrGymIdIsNullAndStatusTrue(Long gymId, Pageable pageable);

	List<RoleJpaEntity> findAllByStatusTrue();

	Page<RoleJpaEntity> findAllByStatusTrue(Pageable pageable);

	boolean existsByName(String name);
}
