package com.conovax.sexbody.infrastructure.security.aspects;

import com.conovax.sexbody.application.services.GymSaasAccessService;
import com.conovax.sexbody.infrastructure.security.GymAuthenticationDetails;
import com.conovax.sexbody.infrastructure.security.annotations.RequireFeature;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RequireFeatureAspect {

	private final GymSaasAccessService gymSaasAccessService;

	@Before("@annotation(requireFeature)")
	public void checkFeature(RequireFeature requireFeature) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new AccessDeniedException("No hay una sesión autenticada para validar la funcionalidad");
		}

		GymAuthenticationDetails details = extractGymDetails(authentication.getDetails());
		Long gymId = details.gymId();
		if (gymId == null) {
			throw new AccessDeniedException("La sesión no tiene un gym seleccionado");
		}

		gymSaasAccessService.requireFeature(gymId, requireFeature.value());
	}

	private GymAuthenticationDetails extractGymDetails(Object details) {
		if (details instanceof GymAuthenticationDetails gymDetails) {
			return gymDetails;
		}
		return new GymAuthenticationDetails(null, null);
	}
}
