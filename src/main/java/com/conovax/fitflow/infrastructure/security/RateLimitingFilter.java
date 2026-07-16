package com.conovax.sexbody.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.conovax.sexbody.application.dto.response.ErrorResponse;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Servlet filter que aplica rate limiting por IP usando Bucket4j (in-memory).
 *
 * Políticas:
 *  - Login:    {@code app.rate-limit.login.capacity}    req / minuto  (default 5)
 *  - Register: {@code app.rate-limit.register.capacity} req / minuto  (default 3)
 *  - API REST: {@code app.rate-limit.api.capacity}      req / minuto  (default 200)
 */
@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    @Value("${app.rate-limit.login.capacity:5}")
    private int loginCapacity;

    @Value("${app.rate-limit.register.capacity:3}")
    private int registerCapacity;

    @Value("${app.rate-limit.api.capacity:200}")
    private int apiCapacity;

    // key = "<category>:<clientIp>"
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Sólo aplica rate limiting a rutas /api/
        if (!path.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = resolveClientIp(request);
        BucketCategory category = resolveCategory(path);
        String bucketKey = category.name() + ":" + clientIp;

        Bucket bucket = buckets.computeIfAbsent(bucketKey, k -> buildBucket(category));

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            long waitSeconds = bucket.getAvailableTokens() >= 0
                    ? 60
                    : bucket.estimateAbilityToConsume(1).getNanosToWaitForRefill() / 1_000_000_000L + 1;

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Retry-After", String.valueOf(waitSeconds));

            ErrorResponse error = new ErrorResponse(
                    HttpStatus.TOO_MANY_REQUESTS.value(),
                    "Too Many Requests",
                    "Has excedido el límite de solicitudes. Intenta de nuevo en " + waitSeconds + " segundos.",
                    path
            );

            objectMapper.writeValue(response.getOutputStream(), error);
        }
    }

    // -------------------------------------------------------------------------

    private enum BucketCategory { LOGIN, REGISTER, API }

    private BucketCategory resolveCategory(String path) {
        if (path.equals("/api/v1/auth/login/gym")) {
            return BucketCategory.LOGIN;
        }
        if (path.equals("/api/v1/auth/register") || path.equals("/api/v1/onboarding/register")) {
            return BucketCategory.REGISTER;
        }
        return BucketCategory.API;
    }

    private Bucket buildBucket(BucketCategory category) {
        int capacity = switch (category) {
            case LOGIN    -> loginCapacity;
            case REGISTER -> registerCapacity;
            case API      -> apiCapacity;
        };
        Bandwidth limit = Bandwidth.builder()
                .capacity(capacity)
                .refillGreedy(capacity, Duration.ofMinutes(1))
                .build();
        return Bucket.builder().addLimit(limit).build();
    }

    /**
     * Resuelve la IP real del cliente teniendo en cuenta proxies / load balancers.
     * Toma sólo la primera IP de X-Forwarded-For para evitar spoofing.
     */
    private String resolveClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            // "client, proxy1, proxy2" — la primera es la IP real
            String first = forwarded.split(",")[0].trim();
            if (!first.isEmpty()) return first;
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp.trim();
        }
        return request.getRemoteAddr();
    }
}
