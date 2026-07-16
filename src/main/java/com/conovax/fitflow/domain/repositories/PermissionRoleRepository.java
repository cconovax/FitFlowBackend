package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.PermissionRole;

public interface PermissionRoleRepository {
	boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

	PermissionRole save(PermissionRole permissionRole);

	void deleteByRoleId(Long roleId);
}
