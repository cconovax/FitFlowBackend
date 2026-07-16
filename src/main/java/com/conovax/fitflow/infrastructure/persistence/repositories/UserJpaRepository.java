package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.UserJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.projections.GymInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

	@Query("SELECT u FROM UserJpaEntity u JOIN FETCH u.people p WHERE p.email = :email")
	Optional<UserJpaEntity> findByEmailWithRolesAndPermissions(String email);

	@Query("SELECT u FROM UserJpaEntity u JOIN FETCH u.people p WHERE (p.email = :login OR p.numDocument = :login)")
	Optional<UserJpaEntity> findByLoginWithRolesAndPermissions(String login);

	@Query(
			nativeQuery = true,
			value = """
				SELECT
					ug.user_gym_id AS userGymId,
					g.gym_id AS id,
					g.name AS name,
					g.logo AS logo,
					m.name AS municipalitie
				FROM users_gyms ug
				JOIN gyms g ON g.gym_id = ug.gym_id
				LEFT JOIN municipalities m ON m.municipalitie_id = g.municipalitie_id
				WHERE ug.user_id = :userId
					AND COALESCE(ug.status, true) = true
					AND g.status = true
				ORDER BY g.gym_id
			"""
	)
	List<GymInfoProjection> findGymsByUserId(@Param("userId") Long userId);

	boolean existsByPeople_Email(String email);

	boolean existsByPeople_NumDocument(String numDocument);

	Optional<UserJpaEntity> findByPeople_NumDocument(String numDocument);
}
