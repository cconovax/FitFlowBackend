package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Solicitud para transferir permisos de un rol a otro")
public record TransferPermissionsRequest(

		@NotNull(message = "El ID del rol origen es requerido")
		@Positive(message = "El ID del rol origen debe ser mayor que 0")
		@Schema(description = "ID del rol cuyos permisos se copiarán", example = "1")
		Long sourceRoleId,

		@NotNull(message = "El ID del rol destino es requerido")
		@Positive(message = "El ID del rol destino debe ser mayor que 0")
		@Schema(description = "ID del rol que recibirá los permisos", example = "2")
		Long targetRoleId
) {
}
