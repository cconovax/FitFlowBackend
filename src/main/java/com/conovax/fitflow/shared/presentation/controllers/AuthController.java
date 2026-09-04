package com.conovax.fitflow.shared.presentation.controllers;

import com.conovax.fitflow.shared.application.dto.request.ForgotPasswordRequest;
import com.conovax.fitflow.shared.application.dto.request.LoginWithGymRequest;
import com.conovax.fitflow.shared.application.dto.request.RegisterRequest;
import com.conovax.fitflow.shared.application.dto.request.ResetPasswordRequest;
import com.conovax.fitflow.shared.application.dto.response.AuthGymResponse;
import com.conovax.fitflow.shared.application.dto.response.CurrentUserResponse;
import com.conovax.fitflow.gym.application.dto.response.UserResponse;
import com.conovax.fitflow.shared.application.services.AuthService;
import com.conovax.fitflow.shared.application.services.PasswordResetService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpirationMs;

    private final Environment env;

    public AuthController(AuthService authService, PasswordResetService passwordResetService, Environment env) {
        this.authService = authService;
        this.passwordResetService = passwordResetService;
        this.env = env;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login/gym")
    public ResponseEntity<AuthGymResponse> loginWithGym(
            @Valid @RequestBody LoginWithGymRequest request,
            HttpServletResponse response) {
        AuthGymResponse result = authService.loginWithGym(request);
        if (result.token() != null) {
            response.addHeader(HttpHeaders.SET_COOKIE, buildAuthCookie(result.token()).toString());
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Boolean>> refresh(HttpServletResponse response) {
        String token = authService.refreshCurrentToken();
        response.addHeader(HttpHeaders.SET_COOKIE, buildAuthCookie(token).toString());
        return ResponseEntity.ok(Map.of("refreshed", true));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE,
                ResponseCookie.from("auth_token", "")
                        .httpOnly(true)
                        .secure(env.acceptsProfiles("prod"))
                        .sameSite(env.acceptsProfiles("prod") ? "Lax" : "Lax")
                        .path("/")
                        .maxAge(0)
                        .build()
                        .toString());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponse> getCurrentUser() {
        CurrentUserResponse response = authService.getCurrentUser();
        return ResponseEntity.ok(response);
    }

    /** Solicita el email de restablecimiento. Siempre devuelve 200 para no filtrar si el email existe. */
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        passwordResetService.requestReset(request.email());
        return ResponseEntity.ok().build();
    }

    /** Valida el token antes de mostrar el formulario de nueva contraseña. */
    @GetMapping("/reset-password/validate")
    public ResponseEntity<Void> validateResetToken(@RequestParam String token) {
        passwordResetService.validateToken(token);
        return ResponseEntity.ok().build();
    }

    /** Restablece la contraseña con el token del email. */
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.ok().build();
    }

    private ResponseCookie buildAuthCookie(String token) {
        return ResponseCookie.from("auth_token", token)
                .httpOnly(true)
                .secure(env.acceptsProfiles("prod"))
                .sameSite(env.acceptsProfiles("prod") ? "Lax" : "Lax")
                .path("/")
                .maxAge(Duration.ofMillis(jwtExpirationMs))
                .build();
    }

}

