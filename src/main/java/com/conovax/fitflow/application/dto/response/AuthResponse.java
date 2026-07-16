package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Set;

@Schema(description = "Respuesta de autenticación con JWT")
public record AuthResponse(
        @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,

        @Schema(description = "Tipo de token", example = "Bearer")
        String type,

        @Schema(description = "ID del usuario", example = "1")
        Long id,

        @Schema(description = "Login usado/retornado (email o número de documento)", example = "john@example.com")
        String login,

        @Schema(description = "Email del usuario (puede ser null si no tiene)", example = "john@example.com")
        String email,

        @Schema(description = "Número de documento", example = "123456789")
        String numDocument,

        @Schema(description = "Nombres del usuario", example = "John")
        String names,

        @Schema(description = "Apellidos del usuario", example = "Doe")
        String surnames,

        @Schema(description = "Teléfono del usuario", example = "3001234567")
        String phone,

        @Schema(description = "Roles del usuario", example = "[{\"name\":\"Super Administrador\",\"full_access\":true}]")
        List<AuthRoleResponse> roles,

        @Schema(description = "Permisos del usuario", example = "[\"user:read\", \"user:write\"]")
        Set<String> permissions,

        @Schema(description = "Gyms asociados al usuario")
        java.util.List<GymInfoResponse> gyms
) {
    public AuthResponse(
            String token,
            Long id,
            String login,
            String email,
            String numDocument,
            String names,
            String surnames,
            String phone,
            List<AuthRoleResponse> roles,
            Set<String> permissions,
            java.util.List<GymInfoResponse> gyms
    ) {
        this(token, "Bearer", id, login, email, numDocument, names, surnames, phone, roles, permissions, gyms);
    }
}
