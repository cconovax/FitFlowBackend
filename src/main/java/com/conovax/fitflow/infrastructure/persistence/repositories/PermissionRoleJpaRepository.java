package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.PermissionRoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRoleJpaRepository extends JpaRepository<PermissionRoleJpaEntity, Long> {
	boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

	void deleteByRoleId(Long roleId);
}
