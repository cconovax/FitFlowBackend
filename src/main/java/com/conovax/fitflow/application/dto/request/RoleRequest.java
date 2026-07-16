package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Solicitud para crear/actualizar un rol")
public record RoleRequest(
		@NotBlank(message = "El nombre del rol es requerido")
		@Size(max = 60, message = "El nombre del rol no puede exceder 60 caracteres")
		@Schema(description = "Nombre del rol", example = "ADMIN")
		String name,

		@NotBlank(message = "El código del rol es requerido")
		@Size(max = 60, message = "El código del rol no puede exceder 60 caracteres")
		@Schema(description = "Código del rol", example = "ADMIN")
		String code,

		@Positive(message = "El gymId debe ser mayor que 0")
		@Schema(description = "ID del gimnasio (opcional)", example = "1")
		Long gymId,

		@Schema(description = "Acceso completo", example = "false")
		Boolean fullAccess,

	@Schema(description = "Es personal del gym (entrenador u otro rol de staff)", example = "false")
	Boolean isStaff,

		@Schema(description = "Estado del rol (activo/inactivo)", example = "true")
		Boolean status,

		@Schema(description = "Lista de IDs de permisos a asignar al rol", example = "[1, 2, 3]")
		List<Long> permissionIds
) {
}
