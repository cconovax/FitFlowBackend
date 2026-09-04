package com.conovax.fitflow.shared.domain.repositories;

import com.conovax.fitflow.shared.domain.entities.PermissionRole;

public interface PermissionRoleRepository {
	boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

	PermissionRole save(PermissionRole permissionRole);

	void deleteByRoleId(Long roleId);
}
