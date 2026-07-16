package com.conovax.sexbody.infrastructure.security.aspects;

import com.conovax.sexbody.infrastructure.security.annotations.RequirePermission;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

	private static final String FULL_ACCESS = "FULL_ACCESS";

	@Before("@annotation(requirePermission)")
	public void checkPermission(JoinPoint joinPoint, RequirePermission requirePermission) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getAuthorities() == null) {
			throw new AccessDeniedException("No tienes permisos para acceder a este recurso");
		}

		String required = requirePermission.value();
		boolean allowed = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(a -> FULL_ACCESS.equals(a) || required.equals(a));

		if (!allowed) {
			throw new AccessDeniedException("No tienes permisos para acceder a este recurso");
		}
	}
}
