package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Solicitud de onboarding: crea cuenta + gimnasio en un solo paso")
public record OnboardingRegisterRequest(

		// ── Datos del usuario ──────────────────────────────────────────────────

		@Schema(description = "Nombres", example = "Juan")
		@NotBlank(message = "Los nombres son requeridos")
		@Size(max = 60)
		String names,

		@Schema(description = "Apellidos", example = "Pérez")
		@NotBlank(message = "Los apellidos son requeridos")
		@Size(max = 60)
		String surnames,

		@Schema(description = "Email (será el login)", example = "juan@example.com")
		@NotBlank(message = "El email es requerido")
		@Email(message = "El email debe ser válido")
		@Size(max = 60)
		String email,

		@Schema(description = "Número de documento", example = "1020304050")
		@NotBlank(message = "El número de documento es requerido")
		@Size(max = 15)
		String numDocument,

		@Schema(description = "Contraseña", example = "MiClave2024!")
		@NotBlank(message = "La contraseña es requerida")
		@Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
		String password,

		@Schema(description = "ID del tipo de documento", example = "1")
		@NotNull(message = "El tipo de documento es requerido")
		@Positive
		Long typeDocumentId,

		@Schema(description = "ID del sexo", example = "1")
		@NotNull(message = "El sexo es requerido")
		@Positive
		Long sexoId,

		@Schema(description = "ID del municipio del usuario", example = "5")
		@NotNull(message = "El municipio es requerido")
		@Positive
		Long municipalitieId,

		// ── Datos del gimnasio ─────────────────────────────────────────────────

		@Schema(description = "Nombre del gimnasio", example = "PowerFit Gym")
		@NotBlank(message = "El nombre del gimnasio es requerido")
		@Size(max = 60)
		String gymName,

		@Schema(description = "NIT del gimnasio", example = "900123456-7")
		@NotBlank(message = "El NIT del gimnasio es requerido")
		@Size(max = 20)
		String gymNit,

		@Schema(description = "ID del municipio del gimnasio", example = "5")
		@NotNull(message = "El municipio del gimnasio es requerido")
		@Positive
		Long gymMunicipalitieId,

		@Schema(description = "Teléfono del gimnasio (opcional)", example = "+573001234567")
		@Size(max = 13)
		String gymPhone,

		@Schema(description = "Email del gimnasio (opcional)", example = "info@powerfitgym.com")
		@Size(max = 100)
		String gymEmail,

		// ── Suscripción SaaS (opcional) ────────────────────────────────────────

		@Schema(description = "ID del plan SaaS seleccionado (null = período de prueba de 14 días)", example = "1")
		Long saasPlanId,

		@Schema(description = "Payment Method ID de Stripe para guardar la tarjeta (opcional)", example = "pm_xxx")
		String paymentMethodId
) {}
