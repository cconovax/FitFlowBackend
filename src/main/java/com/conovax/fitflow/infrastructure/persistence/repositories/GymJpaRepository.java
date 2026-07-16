package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.GymJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface GymJpaRepository extends JpaRepository<GymJpaEntity, Long> {
	List<GymJpaEntity> findAllByStatusTrue();

	Optional<GymJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<GymJpaEntity> findByIdAndStatusFalse(Long id);

	List<GymJpaEntity> findByNameContainingIgnoreCaseAndStatusTrue(String name);

	List<GymJpaEntity> findByNameContainingIgnoreCase(String name);

	Page<GymJpaEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<GymJpaEntity> findByNameContainingIgnoreCaseOrNitContainingIgnoreCase(String name, String nit, Pageable pageable);

	List<GymJpaEntity> findByParentGym_Id(Long parentGymId);
}
