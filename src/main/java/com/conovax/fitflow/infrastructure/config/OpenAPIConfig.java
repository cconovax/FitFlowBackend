package com.conovax.fitflow.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Fitflow Auth API",
                version = "1.0.0",
                description = "API REST para autenticación de usuarios con JWT, roles y permisos. " +
                        "Esta API implementa un sistema completo de autenticación y autorización basado en roles y permisos, " +
                        "donde los usuarios pueden tener múltiples roles y cada rol puede tener múltiples permisos.",
                contact = @Contact(
                        name = "Soporte Técnico",
                        email = "support@example.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Autenticación JWT. Utiliza el token obtenido del endpoint /api/v1/auth/login",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
