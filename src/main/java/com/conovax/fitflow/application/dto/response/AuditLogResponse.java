package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Registro de auditoría: quién accedió, qué hizo y desde qué IP")
public record AuditLogResponse(
		@Schema(description = "ID del registro", example = "1")
		Long id,

		@Schema(description = "ID del gym", example = "5")
		Long gymId,

		@Schema(description = "ID del usuario", example = "12")
		Long userId,

		@Schema(description = "ID de la relación user_gym", example = "8")
		Long userGymId,

		@Schema(description = "Identificador del usuario (email o documento)", example = "admin@gym.com")
		String username,

		@Schema(description = "Acción", example = "UPDATE")
		String action,

		@Schema(description = "Método HTTP", example = "PUT")
		String httpMethod,

		@Schema(description = "Ruta solicitada", example = "/api/v1/gyms/5/devices/3")
		String path,

		@Schema(description = "Query string", example = "page=0&size=20")
		String queryString,

		@Schema(description = "Payload enviado (sanitizado)")
		String payload,

		@Schema(description = "Dirección IP de origen", example = "190.0.0.1")
		String ipAddress,

		@Schema(description = "User-Agent del cliente")
		String userAgent,

		@Schema(description = "Código de estado HTTP de la respuesta", example = "200")
		Integer statusCode,

		@Schema(description = "Fecha y hora del evento")
		LocalDateTime createdAt
) {
}
