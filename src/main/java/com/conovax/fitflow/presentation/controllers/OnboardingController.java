package com.conovax.fitflow.presentation.controllers;

import com.conovax.fitflow.application.dto.request.OnboardingGymRequest;
import com.conovax.fitflow.application.dto.request.OnboardingRegisterRequest;
import com.conovax.fitflow.application.dto.response.AuthGymResponse;
import com.conovax.fitflow.application.dto.response.OnboardingGymResponse;
import com.conovax.fitflow.application.services.OnboardingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/onboarding")
@RequiredArgsConstructor
@Tag(name = "Onboarding", description = "Registro de nuevo gimnasio (self-service)")
public class OnboardingController {

	private final OnboardingService onboardingService;

	@Value("${jwt.expiration:86400000}")
	private long jwtExpirationMs;

	@Operation(
			summary = "Registro completo (cuenta + gimnasio)",
			description = "Endpoint público. Crea la cuenta del usuario, el gimnasio en modo TRIAL de 14 días y devuelve el JWT listo para usar."
	)
	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<AuthGymResponse> selfRegister(
			@RequestPart("data") @Valid OnboardingRegisterRequest request,
			@RequestPart(value = "logo", required = false) MultipartFile logo,
			HttpServletResponse response
	) {
		AuthGymResponse result = onboardingService.selfRegister(request, logo);
		if (result.token() != null) {
			response.addHeader(HttpHeaders.SET_COOKIE,
					ResponseCookie.from("auth_token", result.token())
							.httpOnly(true)
							.secure(true)
							.sameSite("Strict")
							.path("/")
							.maxAge(Duration.ofMillis(jwtExpirationMs))
							.build()
							.toString());
		}
		return ResponseEntity.ok(result);
	}

	@Operation(
			summary = "Crear mi propio gimnasio (usuario ya autenticado)",
			description = "Permite a un usuario ya autenticado crear un gimnasio en modo TRIAL."
	)
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping(value = "/gym", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<OnboardingGymResponse> createOwnGym(
			@RequestPart("data") @Valid OnboardingGymRequest request,
			@RequestPart(value = "logo", required = false) MultipartFile logo
	) {
		return ResponseEntity.ok(onboardingService.createOwnGym(request, logo));
	}
}
