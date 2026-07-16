package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.BenefitJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface BenefitJpaRepository extends JpaRepository<BenefitJpaEntity, Long> {
	List<BenefitJpaEntity> findAllByStatusTrue();

	List<BenefitJpaEntity> findAllByIdInAndStatusTrue(List<Long> ids);

	@Query("SELECT b FROM BenefitJpaEntity b WHERE b.status = true AND (b.gymId = :gymId OR b.gymId IS NULL)")
	List<BenefitJpaEntity> findAllActiveByGymIdOrGlobal(@Param("gymId") Long gymId);

	@Query(
			"""
			SELECT b
			FROM BenefitJpaEntity b
			WHERE (b.gymId = :gymId OR b.gymId IS NULL)
				AND (
					:search IS NULL OR :search = ''
					OR LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%'))
					OR LOWER(b.description) LIKE LOWER(CONCAT('%', :search, '%'))
				)
			ORDER BY LOWER(b.name) ASC
			"""
	)
	Page<BenefitJpaEntity> findAllActiveByGymIdOrGlobalAndSearchFilter(
			@Param("gymId") Long gymId,
			@Param("search") String search,
			Pageable pageable
	);

	Optional<BenefitJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<BenefitJpaEntity> findByIdAndStatusFalse(Long id);

	boolean existsByNameIgnoreCaseAndGymIdAndStatusTrue(String name, Long gymId);

	boolean existsByNameIgnoreCaseAndGymIdAndIdNotAndStatusTrue(String name, Long gymId, Long id);
}
