package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.UsersGymJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.projections.UserGymUserPeopleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersGymJpaRepository extends JpaRepository<UsersGymJpaEntity, Long> {
	boolean existsByUserIdAndGymId(Long userId, Long gymId);

	boolean existsByUserIdAndGymIdAndStatusTrue(Long userId, Long gymId);

	Optional<UsersGymJpaEntity> findByUserIdAndGymId(Long userId, Long gymId);

	List<UsersGymJpaEntity> findAllByUserId(Long userId);

	Optional<UsersGymJpaEntity> findByIdAndStatusTrue(Long id);

	Optional<UsersGymJpaEntity> findByIdAndStatusFalse(Long id);

	Optional<UsersGymJpaEntity> findByIdAndGymIdAndStatusTrue(Long id, Long gymId);

	Optional<UsersGymJpaEntity> findByIdAndGymIdAndStatusFalse(Long id, Long gymId);

	@Query(
			nativeQuery = true,
			value = """
				SELECT
					ug.user_gym_id AS userGymId,
					u.user_id AS userId,
					ug.status AS userGymStatus,
					p.people_id AS peopleId,
					p.num_document AS numDocument,
					p.email AS email,
					p.names AS names,
					p.surnames AS surnames,
					p.phone AS phone
				FROM users_gyms ug
				JOIN users u ON u.user_id = ug.user_id
				JOIN peoples p ON p.people_id = u.people_id
				WHERE ug.gym_id = :gymId
					AND p.status = true
					AND (
						:search IS NULL OR :search = ''
						OR LOWER(p.names) LIKE LOWER(CONCAT('%', :search, '%'))
						OR LOWER(p.surnames) LIKE LOWER(CONCAT('%', :search, '%'))
						OR LOWER(CONCAT(p.names, ' ', p.surnames)) LIKE LOWER(CONCAT('%', :search, '%'))
						OR LOWER(p.num_document) LIKE LOWER(CONCAT('%', :search, '%'))
					)
					AND NOT EXISTS (
						SELECT 1
						FROM users_gym_role ugr
						JOIN roles r ON r.role_id = ugr.role_id
						WHERE ugr.user_gym_id = ug.user_gym_id
							AND r.full_access = true
					)
				ORDER BY ug.user_gym_id DESC
			""",
			countQuery = """
				SELECT COUNT(*)
				FROM users_gyms ug
				JOIN users u ON u.user_id = ug.user_id
				JOIN peoples p ON p.people_id = u.people_id
				WHERE ug.gym_id = :gymId
					AND p.status = true
					AND (
						:search IS NULL OR :search = ''
						OR LOWER(p.names) LIKE LOWER(CONCAT('%', :search, '%'))
						OR LOWER(p.surnames) LIKE LOWER(CONCAT('%', :search, '%'))
						OR LOWER(CONCAT(p.names, ' ', p.surnames)) LIKE LOWER(CONCAT('%', :search, '%'))
						OR LOWER(p.num_document) LIKE LOWER(CONCAT('%', :search, '%'))
					)
					AND NOT EXISTS (
						SELECT 1
						FROM users_gym_role ugr
						JOIN roles r ON r.role_id = ugr.role_id
						WHERE ugr.user_gym_id = ug.user_gym_id
							AND r.full_access = true
					)
			"""
	)
	Page<UserGymUserPeopleProjection> findUsersByGymId(
			@Param("gymId") Long gymId,
			@Param("search") String search,
			Pageable pageable
	);

	@Query(
			nativeQuery = true,
			value = """
				SELECT
					ug.user_gym_id AS userGymId,
					u.user_id AS userId,
					ug.status AS userGymStatus,
					p.people_id AS peopleId,
					p.num_document AS numDocument,
					p.email AS email,
					p.names AS names,
					p.surnames AS surnames,
					p.phone AS phone
				FROM users_gyms ug
				JOIN users u ON u.user_id = ug.user_id
				JOIN peoples p ON p.people_id = u.people_id
				WHERE ug.user_gym_id = :userGymId
			"""
	)
	UserGymUserPeopleProjection findUserGymPeopleById(@Param("userGymId") Long userGymId);

	@Query(
			nativeQuery = true,
			value = """
				SELECT
					ug.user_gym_id AS userGymId,
					u.user_id AS userId,
					ug.status AS userGymStatus,
					p.people_id AS peopleId,
					p.num_document AS numDocument,
					p.email AS email,
					p.names AS names,
					p.surnames AS surnames,
					p.phone AS phone
				FROM users_gyms ug
				JOIN users u ON u.user_id = ug.user_id
				JOIN peoples p ON p.people_id = u.people_id
				JOIN users_gym_role ugr ON ugr.user_gym_id = ug.user_gym_id
				JOIN roles r ON r.role_id = ugr.role_id
				WHERE ug.gym_id = :gymId
					AND p.status = true
					AND r.name = 'Entrenador'
					AND (
						:search IS NULL OR :search = ''
						OR LOWER(p.names) LIKE LOWER(CONCAT('%', :search, '%'))
						OR LOWER(p.surnames) LIKE LOWER(CONCAT('%', :search, '%'))
						OR LOWER(CONCAT(p.names, ' ', p.surnames)) LIKE LOWER(CONCAT('%', :search, '%'))
						OR LOWER(p.num_document) LIKE LOWER(CONCAT('%', :search, '%'))
					)
				ORDER BY ug.user_gym_id DESC
			"""
	)
	List<UserGymUserPeopleProjection> findTrainersByGymId(
			@Param("gymId") Long gymId,
			@Param("search") String search
	);

	@Query("SELECT COUNT(ug) FROM UsersGymJpaEntity ug WHERE ug.gymId = :gymId AND ug.status = true")
	long countActiveByGymId(@Param("gymId") Long gymId);

	@Query("SELECT COUNT(ug) FROM UsersGymJpaEntity ug WHERE ug.gymId = :gymId")
	long countTotalByGymId(@Param("gymId") Long gymId);
}
