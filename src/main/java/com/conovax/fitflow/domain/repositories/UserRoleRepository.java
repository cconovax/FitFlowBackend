package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.UsersGymRole;

import java.util.List;

public interface UserRoleRepository {
	boolean existsByUserGymIdAndRoleId(Long userGymId, Long roleId);

	void deleteByUserGymId(Long userGymId);

	List<Long> findRoleIdsByUserGymId(Long userGymId);

	List<Long> findRoleIdsByUserId(Long userId);

	UsersGymRole save(UsersGymRole entity);
}
