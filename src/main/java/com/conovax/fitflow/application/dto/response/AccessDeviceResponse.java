package com.conovax.fitflow.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Respuesta con información de un dispositivo de acceso")
public record AccessDeviceResponse(
		@Schema(description = "ID del dispositivo", example = "1")
		Long id,

		@Schema(description = "ID del gym al que pertenece", example = "5")
		Long gymId,

		@Schema(description = "Nombre descriptivo del dispositivo", example = "Lector entrada principal")
		String name,

		@Schema(description = "Tipo de dispositivo", example = "FINGERPRINT_READER")
		String deviceType,

		@Schema(description = "Número de serie", example = "SN-12345")
		String serialNumber,

		@Schema(description = "Dirección MAC", example = "AA:BB:CC:DD:EE:FF")
		String macAddress,

		@Schema(description = "Ubicación física", example = "Entrada principal")
		String location,

		@Schema(description = "Estado del dispositivo", example = "true")
		Boolean status,

		@Schema(description = "Fecha de registro")
		LocalDateTime createdAt,

		@Schema(description = "Fecha de última actualización")
		LocalDateTime updatedAt
) {
}
