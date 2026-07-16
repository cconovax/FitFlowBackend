package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos para el registro de un nuevo gimnasio en modo onboarding (self-service)")
public record OnboardingGymRequest(

		@Schema(description = "Nombre del gimnasio", example = "Mi Gym")
		@NotBlank(message = "El nombre es requerido")
		@Size(max = 60, message = "El nombre no puede superar los 60 caracteres")
		String name,

		@Schema(description = "NIT del gimnasio", example = "900123456-7")
		@NotBlank(message = "El NIT es requerido")
		@Size(max = 20, message = "El NIT no puede superar los 20 caracteres")
		String nit,

		@Schema(description = "ID del municipio", example = "5")
		@NotNull(message = "El municipio es requerido")
		Long municipalitieId,

		@Schema(description = "Teléfono de contacto (opcional)", example = "+573001234567")
		@Size(max = 13)
		String phone,

		@Schema(description = "Email de contacto (opcional)", example = "info@migym.com")
		@Size(max = 100)
		String email
) {}
