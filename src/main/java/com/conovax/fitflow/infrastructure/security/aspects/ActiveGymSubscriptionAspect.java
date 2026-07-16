package com.conovax.sexbody.infrastructure.security.aspects;

import com.conovax.sexbody.application.services.GymSubscriptionService;
import com.conovax.sexbody.domain.exceptions.SubscriptionInactiveException;
import com.conovax.sexbody.infrastructure.security.GymAuthenticationDetails;
import com.conovax.sexbody.infrastructure.security.annotations.RequireActiveGymSubscription;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class ActiveGymSubscriptionAspect {

	private final GymSubscriptionService gymSubscriptionService;

	@Before("@annotation(requireActiveGymSubscription)")
	public void checkSubscription(JoinPoint joinPoint, RequireActiveGymSubscription requireActiveGymSubscription) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new AccessDeniedException("No hay una sesión autenticada para validar la suscripción del gym");
		}

		GymAuthenticationDetails details = extractGymDetails(authentication.getDetails());
		Long selectedGymId = details.gymId();
		if (selectedGymId == null) {
			throw new AccessDeniedException("La sesión no tiene un gym seleccionado");
		}

		if (!gymSubscriptionService.hasActiveSubscription(selectedGymId)) {
			throw new SubscriptionInactiveException("El gym seleccionado no tiene una suscripción activa");
		}

		if (!requireActiveGymSubscription.enforceGymMatch()) {
			return;
		}

		Optional<Long> requestGymId = resolveRequestGymId(joinPoint, requireActiveGymSubscription.gymIdVariable());
		if (requestGymId.isPresent() && !selectedGymId.equals(requestGymId.get())) {
			throw new AccessDeniedException("No puedes operar sobre un gym diferente al seleccionado en la sesión");
		}
	}

	private GymAuthenticationDetails extractGymDetails(Object details) {
		if (details instanceof GymAuthenticationDetails gymDetails) {
			return gymDetails;
		}
		return new GymAuthenticationDetails(null, null);
	}

	private Optional<Long> resolveRequestGymId(JoinPoint joinPoint, String gymIdVariable) {
		HttpServletRequest request = currentRequest();
		if (request != null) {
			Object rawPathVariables = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			if (rawPathVariables instanceof Map<?, ?> pathVariables) {
				Object rawGymId = pathVariables.get(gymIdVariable);
				Long parsed = parseLong(rawGymId);
				if (parsed != null) {
					return Optional.of(parsed);
				}
			}

			Long queryGymId = parseLong(request.getParameter(gymIdVariable));
			if (queryGymId != null) {
				return Optional.of(queryGymId);
			}
		}

		for (Object arg : joinPoint.getArgs()) {
			Long candidate = extractGymIdFromArgument(arg, gymIdVariable);
			if (candidate != null) {
				return Optional.of(candidate);
			}
		}

		return Optional.empty();
	}

	private Long extractGymIdFromArgument(Object arg, String gymIdVariable) {
		if (arg == null) {
			return null;
		}

		Long fromAccessor = invokeLongAccessor(arg, gymIdVariable);
		if (fromAccessor != null) {
			return fromAccessor;
		}

		String getterName = "get" + Character.toUpperCase(gymIdVariable.charAt(0)) + gymIdVariable.substring(1);
		return invokeLongAccessor(arg, getterName);
	}

	private Long invokeLongAccessor(Object target, String methodName) {
		try {
			Method method = target.getClass().getMethod(methodName);
			Object value = method.invoke(target);
			return parseLong(value);
		} catch (ReflectiveOperationException ignored) {
			return null;
		}
	}

	private Long parseLong(Object rawValue) {
		if (rawValue == null) {
			return null;
		}
		if (rawValue instanceof Number number) {
			return number.longValue();
		}
		String value = String.valueOf(rawValue).trim();
		if (value.isBlank()) {
			return null;
		}
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ignored) {
			return null;
		}
	}

	private HttpServletRequest currentRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
			return servletRequestAttributes.getRequest();
		}
		return null;
	}
}