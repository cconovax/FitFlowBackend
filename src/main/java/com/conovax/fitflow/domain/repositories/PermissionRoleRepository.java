package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.PermissionRole;

public interface PermissionRoleRepository {
	boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

	PermissionRole save(PermissionRole permissionRole);

	void deleteByRoleId(Long roleId);
}
