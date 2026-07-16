package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Schema(description = "Solicitud para relacionar un usuario existente a un gym")
public record GymUserRelationRequest(
		@NotBlank(message = "El número de documento es requerido")
		@Schema(description = "Número de documento del usuario", example = "1020304050")
		@Size(max = 15, message = "El número de documento no puede exceder 15 caracteres")
		String numDocument,

		@Schema(description = "IDs de gyms a relacionar. En el endpoint con /gyms/{gymId}, este campo es opcional y se combina con el gymId de la ruta.", example = "[1,2,3]")
		Set<@NotNull(message = "Cada gymId es requerido") @Positive(message = "Cada gymId debe ser positivo") Long> gymIds,

		@NotEmpty(message = "Debe enviar al menos un roleId")
		@Schema(description = "Roles a asignar a la relación user_gym", example = "[1,2]")
		List<@NotNull(message = "Cada roleId es requerido") @Positive(message = "Cada roleId debe ser positivo") Long> roleIds,

		@Nullable()
		@Schema(
				description = "Huella del usuario (bytes). En JSON se envía como Base64.",
				example = "AAECAwQFBgcICQoLDA0ODw=="
		)
		@Size(min = 1, message = "El fingerprint no puede estar vacío")
		byte[] fingerprint
) {
}
