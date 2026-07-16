package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta para validación de existencia de número de documento en People")
public record PeopleNumDocumentExistsResponse(
		@Schema(description = "Indica si el número de documento ya está registrado (People activo: status=true)")
		Boolean exists
) {
}
