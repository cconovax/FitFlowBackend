package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Rol del usuario en la respuesta de autenticación")
public record AuthRoleResponse(
		@Schema(description = "ID del rol", example = "2")
		Long id,

        @Schema(description = "Nombre del rol", example = "Super Administrador")
        String name,

        @Schema(description = "Indica si el rol tiene acceso total", example = "true")
        Boolean fullAccess
) {
}
