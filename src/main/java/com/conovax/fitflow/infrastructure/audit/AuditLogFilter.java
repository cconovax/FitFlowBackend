package com.conovax.fitflow.infrastructure.audit;

import com.conovax.fitflow.application.services.AuditLogService;
import com.conovax.fitflow.domain.entities.AuditLog;
import com.conovax.fitflow.infrastructure.security.GymAuthenticationDetails;
import com.conovax.fitflow.infrastructure.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Registra cada petición a la API como un evento de auditoría: quién (usuario / user_gym),
 * qué (método, ruta, payload enviado), desde qué IP, y el resultado (status HTTP).
 *
 * <p>Se ejecuta después de {@code JwtAuthenticationFilter} para que el {@code SecurityContext}
 * ya esté poblado. Cualquier fallo de auditoría se traga silenciosamente para no afectar la
 * petición original.</p>
 */
@Component
@RequiredArgsConstructor
public class AuditLogFilter extends OncePerRequestFilter {

	private static final int MAX_PAYLOAD = 4000;
	private static final int MAX_PATH = 300;
	private static final int MAX_QUERY = 500;
	private static final int MAX_USER_AGENT = 300;
	private static final int MAX_USERNAME = 80;

	/** Rutas que no generan ruido de auditoría. */
	private static final List<String> IGNORED_PREFIXES = List.of(
			"/swagger-ui", "/api-docs", "/uploads", "/error", "/api/v1/webhooks", "/favicon.ico"
	);

	/** Campos sensibles a redactar dentro del payload JSON. */
	private static final Pattern SENSITIVE_FIELDS = Pattern.compile(
			"(\"(?:password|fingerprint|token|secret|clientSecret|access_token)\"\\s*:\\s*)\"[^\"]*\"",
			Pattern.CASE_INSENSITIVE
	);

	private static final Pattern EMAIL_IN_BODY = Pattern.compile(
			"\"email\"\\s*:\\s*\"([^\"]*)\"", Pattern.CASE_INSENSITIVE
	);

	private final AuditLogService auditLogService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {

		if (shouldSkip(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		ContentCachingRequestWrapper wrapped = new ContentCachingRequestWrapper(request);
		try {
			filterChain.doFilter(wrapped, response);
		} finally {
			try {
				record(wrapped, response);
			} catch (Exception ignored) {
				// La auditoría nunca debe romper la petición.
			}
		}
	}

	private boolean shouldSkip(HttpServletRequest request) {
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			return true;
		}
		String uri = request.getRequestURI();
		if (uri == null) {
			return true;
		}
		// El propio endpoint de consulta de auditoría no se audita (evita auto-ruido).
		if (uri.contains("/audit-logs")) {
			return true;
		}
		for (String prefix : IGNORED_PREFIXES) {
			if (uri.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	private void record(ContentCachingRequestWrapper request, HttpServletResponse response) {
		String method = request.getMethod();
		String uri = request.getRequestURI();
		int status = response.getStatus();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Long userId = null;
		String username = null;
		Long gymId = null;
		Long userGymId = null;

		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			username = authentication.getName();
			if (authentication.getPrincipal() instanceof UserDetailsImpl details
					&& details.getUser() != null) {
				userId = details.getUser().getId();
			}
			if (authentication.getDetails() instanceof GymAuthenticationDetails gymDetails) {
				gymId = gymDetails.gymId();
				userGymId = gymDetails.userGymId();
			}
		}

		String action = resolveAction(method, uri, status);
		String payload = buildPayload(request, method);

		// En login (éxito o fallo) el usuario puede no estar en el contexto: tomamos el email del body.
		if (username == null && isAuthPath(uri)) {
			username = extractEmailFromBody(request);
		}

		AuditLog log = AuditLog.builder()
				.gymId(gymId)
				.userId(userId)
				.userGymId(userGymId)
				.username(truncate(username, MAX_USERNAME))
				.action(action)
				.httpMethod(method)
				.path(truncate(uri, MAX_PATH))
				.queryString(truncate(request.getQueryString(), MAX_QUERY))
				.payload(payload)
				.ipAddress(resolveClientIp(request))
				.userAgent(truncate(request.getHeader("User-Agent"), MAX_USER_AGENT))
				.statusCode(status)
				.build();

		auditLogService.record(log);
	}

	private boolean isAuthPath(String uri) {
		return uri != null && uri.contains("/auth/login");
	}

	private String resolveAction(String method, String uri, int status) {
		if (uri != null && uri.contains("/auth/login")) {
			return status >= 400 ? "LOGIN_FAILED" : "LOGIN";
		}
		if (uri != null && uri.contains("/auth/logout")) {
			return "LOGOUT";
		}
		return switch (method == null ? "" : method.toUpperCase()) {
			case "GET" -> "VIEW";
			case "POST" -> "CREATE";
			case "PUT", "PATCH" -> "UPDATE";
			case "DELETE" -> "DELETE";
			default -> "OTHER";
		};
	}

	private String buildPayload(ContentCachingRequestWrapper request, String method) {
		if (!isMutating(method)) {
			return null;
		}
		String contentType = request.getContentType();
		if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			return "[multipart/form-data omitido]";
		}
		byte[] body = request.getContentAsByteArray();
		if (body.length == 0) {
			return null;
		}
		String raw = new String(body, StandardCharsets.UTF_8);
		return truncate(sanitize(raw), MAX_PAYLOAD);
	}

	private boolean isMutating(String method) {
		return "POST".equalsIgnoreCase(method)
				|| "PUT".equalsIgnoreCase(method)
				|| "PATCH".equalsIgnoreCase(method);
	}

	private String sanitize(String json) {
		if (json == null || json.isBlank()) {
			return json;
		}
		Matcher matcher = SENSITIVE_FIELDS.matcher(json);
		return matcher.replaceAll("$1\"***\"");
	}

	private String extractEmailFromBody(ContentCachingRequestWrapper request) {
		byte[] body = request.getContentAsByteArray();
		if (body.length == 0) {
			return null;
		}
		Matcher matcher = EMAIL_IN_BODY.matcher(new String(body, StandardCharsets.UTF_8));
		return matcher.find() ? matcher.group(1) : null;
	}

	private String resolveClientIp(HttpServletRequest request) {
		String forwarded = request.getHeader("X-Forwarded-For");
		if (StringUtils.hasText(forwarded)) {
			return forwarded.split(",")[0].trim();
		}
		String realIp = request.getHeader("X-Real-IP");
		if (StringUtils.hasText(realIp)) {
			return realIp.trim();
		}
		return request.getRemoteAddr();
	}

	private String truncate(String value, int max) {
		if (value == null) {
			return null;
		}
		return value.length() <= max ? value : value.substring(0, max);
	}
}
