package com.conovax.sexbody.presentation.controllers;

import com.conovax.sexbody.application.dto.request.LoginWithGymRequest;
import com.conovax.sexbody.application.dto.request.RegisterRequest;
import com.conovax.sexbody.application.dto.response.AuthGymResponse;
import com.conovax.sexbody.application.dto.response.CurrentUserResponse;
import com.conovax.sexbody.application.dto.response.UserResponse;
import com.conovax.sexbody.application.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpirationMs;

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
                        .secure(true)
                        .sameSite("Strict")
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

    private ResponseCookie buildAuthCookie(String token) {
        return ResponseCookie.from("auth_token", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofMillis(jwtExpirationMs))
                .build();
    }

}

