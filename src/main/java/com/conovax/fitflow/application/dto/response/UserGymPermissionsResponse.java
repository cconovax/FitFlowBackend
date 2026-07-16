package com.conovax.sexbody.application.dto.response;

import java.util.List;

public record UserGymPermissionsResponse(
		List<AuthRoleResponse> roles,
		List<String> permissions
) {
}
