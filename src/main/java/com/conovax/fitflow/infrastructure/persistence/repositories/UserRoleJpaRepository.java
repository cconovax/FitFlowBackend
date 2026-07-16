package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.UsersGymRoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UsersGymRoleJpaEntity, Long> {
	boolean existsByUserGymIdAndRoleId(Long userGymId, Long roleId);

	void deleteByUserGymId(Long userGymId);

	@Query("select ur.roleId from UsersGymRoleJpaEntity ur where ur.userGymId = :userGymId")
	List<Long> findRoleIdsByUserGymId(@Param("userGymId") Long userGymId);

	@Query(
			"select ur.roleId "
					+ "from UsersGymRoleJpaEntity ur "
					+ "join UsersGymJpaEntity ug on ug.id = ur.userGymId "
					+ "where ug.userId = :userId and ug.status = true"
	)
	List<Long> findRoleIdsByUserId(@Param("userId") Long userId);
}
