package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

@Schema(description = "Solicitud para crear/actualizar una membresía")
public record MembershipRequest(
		@NotBlank(message = "El nombre es obligatorio")
		@Size(max = 80, message = "El nombre no puede exceder 80 caracteres")
		@Schema(description = "Nombre", example = "Mensual")
		String name,

		@NotBlank(message = "La descripción es obligatoria")
		@Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
		@Schema(description = "Descripción", example = "Acceso ilimitado durante 30 días")
		String description,

		@NotNull(message = "La duración en días es obligatoria")
		@Positive(message = "La duración en días debe ser mayor que 0")
		@Schema(description = "Duración en días", example = "30")
		Integer durationDay,

		@DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
		@Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 2 decimales")
		@Schema(description = "Precio de la membresía", example = "120000.00")
		BigDecimal price,

		@Positive(message = "El gymId debe ser mayor que 0")
		@Schema(
				description = "ID del gimnasio (opcional). Si es null, la membresía es global",
				example = "1",
				nullable = true,
				requiredMode = Schema.RequiredMode.NOT_REQUIRED
		)
		Long gymId,

		@Schema(
				description = "IDs de beneficios asociados (opcional)",
				example = "[1, 2, 3]",
				nullable = true,
				requiredMode = Schema.RequiredMode.NOT_REQUIRED
		)
		Set<Long> benefitIds
) {
}
