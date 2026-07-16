package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Respuesta con datos de People")
public record PeopleResponse(
		@Schema(description = "ID de la persona", example = "7")
		Long id,

		@Schema(description = "Nombres", example = "John")
		String names,

		@Schema(description = "Apellidos", example = "Doe")
		String surnames,

		@Schema(description = "Teléfono", example = "+573001112233")
		String phone,

		@Schema(description = "Email", example = "john@example.com")
		String email,

		@Schema(description = "Foto", example = "/uploads/logo/profile-default.svg")
		String photo,

		@Schema(description = "ID del municipio", example = "1")
		Long municipalitieId,

		@Schema(description = "ID del sexo", example = "1")
		Long sexoId,

		@Schema(description = "ID del tipo de documento", example = "1")
		Long typeDocumentId,

		@Schema(description = "Número de documento", example = "1020304050")
		String numDocument,

		@Schema(description = "Estado")
		Boolean status,

		@Schema(description = "Fecha de creación")
		LocalDateTime createdAt,

		@Schema(description = "Fecha de actualización")
		LocalDateTime updatedAt
) {
}
