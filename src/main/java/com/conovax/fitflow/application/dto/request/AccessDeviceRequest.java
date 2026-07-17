package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para registrar o actualizar un dispositivo de acceso")
public record AccessDeviceRequest(
		@NotBlank(message = "El nombre del dispositivo es requerido")
		@Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
		@Schema(description = "Nombre descriptivo del dispositivo", example = "Lector entrada principal")
		String name,

		@NotBlank(message = "El tipo de dispositivo es requerido")
		@Size(max = 40, message = "El tipo no puede exceder 40 caracteres")
		@Schema(description = "Tipo de dispositivo", example = "FINGERPRINT_READER",
				allowableValues = {"FINGERPRINT_READER", "BARCODE_SCANNER", "NFC_READER", "PC", "OTHER"})
		String deviceType,

		@Size(max = 100, message = "El número de serie no puede exceder 100 caracteres")
		@Schema(description = "Número de serie del dispositivo", example = "SN-12345")
		String serialNumber,

		@Size(max = 17, message = "La MAC address no puede exceder 17 caracteres")
		@Schema(description = "Dirección MAC del dispositivo", example = "AA:BB:CC:DD:EE:FF")
		String macAddress,

		@Size(max = 120, message = "La ubicación no puede exceder 120 caracteres")
		@Schema(description = "Ubicación física del dispositivo", example = "Entrada principal")
		String location,

		@Schema(description = "Estado del dispositivo", example = "true")
		Boolean status
) {
}
