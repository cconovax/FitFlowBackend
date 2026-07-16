package com.conovax.sexbody.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Respuesta de error")
public record ErrorResponse(
		@Schema(description = "Código de estado HTTP", example = "400")
		Integer status,

		@Schema(description = "Mensaje de error", example = "Bad Request")
		String error,

		@Schema(description = "Descripción detallada del error", example = "El recurso no existe")
		String message,

		@Schema(description = "Ruta del endpoint donde ocurrió el error", example = "/api/v1/products")
		String path,

		@Schema(description = "Timestamp del error")
		LocalDateTime timestamp
) {
	public ErrorResponse(Integer status, String error, String message, String path) {
		this(status, error, message, path, LocalDateTime.now());
	}

	public ErrorResponse {
		if (timestamp == null) {
			timestamp = LocalDateTime.now();
		}
	}
}
