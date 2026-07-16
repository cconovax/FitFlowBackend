package com.conovax.sexbody.infrastructure.config;

import com.conovax.sexbody.infrastructure.audit.AuditLogFilter;
import com.conovax.sexbody.infrastructure.security.RateLimitingFilter;
import com.conovax.sexbody.infrastructure.security.UserDetailsServiceImpl;
import com.conovax.sexbody.infrastructure.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RateLimitingFilter rateLimitingFilter;
    private final AuditLogFilter auditLogFilter;
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${springdoc.api-docs.enabled}")
    private boolean apiDocsEnabled;

    @Value("${springdoc.swagger-ui.enabled}")
    private boolean swaggerUiEnabled;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider authenticationProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    var authorize = auth
                            .requestMatchers(
                                    "/api/v1/auth/login/gym",
                                    "/api/v1/auth/register",
                                    "/api/v1/auth/logout",
                                    "/api/v1/users/gyms",
                                    "/api/v1/onboarding/register"
                            ).permitAll()
                            .requestMatchers(HttpMethod.GET,
                                    "/api/v1/sexos",
                                    "/api/v1/type-documents",
                                    "/api/v1/countries",
                                    "/api/v1/departaments/**",
                                    "/api/v1/municipalities/**",
                                    "/api/v1/saas/plans/available"
                            ).permitAll();
                    if (apiDocsEnabled || swaggerUiEnabled) {
                        authorize = authorize.requestMatchers(
                                "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
                        ).permitAll();
                    }
                    authorize
                            .requestMatchers("/uploads/**").permitAll()
                            .requestMatchers("/api/v1/webhooks/**").permitAll()
                            .requestMatchers("/error").permitAll()
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .anyRequest().authenticated();
                })
                .authenticationProvider(authenticationProvider)
                // El JWT se registra primero (anclado a un filtro estándar) para que su
                // orden quede disponible y los demás filtros puedan referenciarlo.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(rateLimitingFilter, JwtAuthenticationFilter.class)
                .addFilterAfter(auditLogFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.allowed-origins:*}") String allowedOrigins,
            @Value("${app.cors.allow-credentials:false}") boolean allowCredentials
    ) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(allowCredentials);

        List<String> origins = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
        config.setAllowedOriginPatterns(origins.isEmpty() ? List.of("*") : origins);

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
