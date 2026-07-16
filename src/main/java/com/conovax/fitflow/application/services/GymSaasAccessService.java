package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.response.GymSubscriptionResponse;
import com.conovax.sexbody.application.dto.response.SaasPlanResponse;
import com.conovax.sexbody.domain.exceptions.FeatureNotAvailableException;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GymSaasAccessService {

	private final GymSubscriptionService gymSubscriptionService;
	private final SaasPlanService saasPlanService;

	/**
	 * Verifica si el gym tiene acceso a una feature de su plan SaaS activo.
	 * Reglas:
	 * - La suscripción debe estar activa.
	 * - El plan debe existir y contener la feature.
	 * - Si la feature tiene valueBoolean = false, se considera deshabilitada.
	 */
	@Transactional(readOnly = true)
	public boolean hasFeature(Long gymId, String featureCode) {
		GymSubscriptionResponse sub = gymSubscriptionService.getCurrentByGymId(gymId);
		if (!Boolean.TRUE.equals(sub.active())) {
			return false;
		}
		if (sub.saasPlanId() == null) {
			return false;
		}

		SaasPlanResponse plan;
		try {
			plan = saasPlanService.getById(sub.saasPlanId());
		} catch (ResourceNotFoundException ex) {
			return false;
		}

		return plan.features().stream()
				.anyMatch(f -> featureCode.equalsIgnoreCase(f.featureCode())
						&& !Boolean.FALSE.equals(f.valueBoolean()));
	}

	/**
	 * Lanza FeatureNotAvailableException si el gym no tiene acceso a la feature.
	 */
	@Transactional(readOnly = true)
	public void requireFeature(Long gymId, String featureCode) {
		if (!hasFeature(gymId, featureCode)) {
			throw new FeatureNotAvailableException(
					"El plan SaaS del gym no incluye la funcionalidad requerida: " + featureCode
			);
		}
	}
}
