package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.GymSubscriptionRequest;
import com.conovax.fitflow.application.dto.response.GymSubscriptionResponse;
import com.conovax.fitflow.application.dto.response.SaasPlanFeatureResponse;
import com.conovax.fitflow.application.dto.response.SaasPlanResponse;
import com.conovax.fitflow.domain.entities.GymSubscription;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.GymRepository;
import com.conovax.fitflow.domain.repositories.GymSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GymSubscriptionService {

	private final GymSubscriptionRepository gymSubscriptionRepository;
	private final GymRepository gymRepository;
	private final SaasPlanService saasPlanService;

	@Transactional(readOnly = true)
	public GymSubscriptionResponse getCurrentByGymId(Long gymId) {
		ensureGymExists(gymId);
		List<GymSubscription> subscriptions = gymSubscriptionRepository.findAllByGymIdOrderByEndDateDescIdDesc(gymId);
		if (subscriptions.isEmpty()) {
			return emptyResponse(gymId);
		}

		GymSubscription active = findActive(subscriptions);
		return toResponse(active != null ? active : subscriptions.get(0));
	}

	@Transactional(readOnly = true)
	public List<GymSubscriptionResponse> getHistoryByGymId(Long gymId) {
		ensureGymExists(gymId);
		return gymSubscriptionRepository.findAllByGymIdOrderByEndDateDescIdDesc(gymId).stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional
	public GymSubscriptionResponse create(Long gymId, GymSubscriptionRequest request) {
		ensureGymExists(gymId);
		validateDates(request.startDate(), request.endDate());

		saasPlanService.getById(request.saasPlanId());

		GymSubscription saved = gymSubscriptionRepository.save(GymSubscription.builder()
				.gymId(gymId)
				.saasPlanId(request.saasPlanId())
				.startDate(request.startDate())
				.endDate(request.endDate())
				.status(request.status() == null ? Boolean.TRUE : request.status())
				.notes(request.notes())
				.build());
		return toResponse(saved);
	}

	@Transactional
	public GymSubscriptionResponse update(Long gymId, Long subscriptionId, GymSubscriptionRequest request) {
		ensureGymExists(gymId);
		validateDates(request.startDate(), request.endDate());
		saasPlanService.getById(request.saasPlanId());

		GymSubscription existing = gymSubscriptionRepository.findById(subscriptionId)
				.orElseThrow(() -> new ResourceNotFoundException("Suscripción no encontrada con ID: " + subscriptionId));

		if (!gymId.equals(existing.getGymId())) {
			throw new IllegalArgumentException("La suscripción no pertenece al gym indicado");
		}

		GymSubscription updated = existing.toBuilder()
				.saasPlanId(request.saasPlanId())
				.startDate(request.startDate())
				.endDate(request.endDate())
				.status(request.status() == null ? Boolean.TRUE : request.status())
				.notes(request.notes())
				.build();

		return toResponse(gymSubscriptionRepository.save(updated));
	}

	@Transactional(readOnly = true)
	public boolean hasActiveSubscription(Long gymId) {
		return getCurrentByGymId(gymId).active();
	}

	/**
	 * Desactiva la suscripción interna activa del gym (status = false).
	 * Se llama cuando Stripe confirma la cancelación de la suscripción.
	 */
	@Transactional
	public void deactivateCurrentSubscription(Long gymId) {
		List<GymSubscription> subscriptions = gymSubscriptionRepository.findAllByGymIdOrderByEndDateDescIdDesc(gymId);
		LocalDate today = LocalDate.now();
		subscriptions.stream()
				.filter(s -> isActive(s, today))
				.forEach(s -> gymSubscriptionRepository.save(s.toBuilder().status(Boolean.FALSE).build()));
	}

	private GymSubscription findActive(List<GymSubscription> subscriptions) {
		LocalDate today = LocalDate.now();
		return subscriptions.stream()
				.filter(subscription -> isActive(subscription, today))
				.findFirst()
				.orElse(null);
	}

	private boolean isActive(GymSubscription subscription, LocalDate today) {
		return Boolean.TRUE.equals(subscription.getStatus())
				&& subscription.getStartDate() != null
				&& subscription.getEndDate() != null
				&& !today.isBefore(subscription.getStartDate())
				&& !today.isAfter(subscription.getEndDate());
	}

	private GymSubscriptionResponse toResponse(GymSubscription subscription) {
		LocalDate today = LocalDate.now();
		boolean active = isActive(subscription, today);
		Long daysRemaining = null;
		if (subscription.getEndDate() != null) {
			long diff = ChronoUnit.DAYS.between(today, subscription.getEndDate());
			daysRemaining = active ? diff + 1 : Math.max(diff, 0);
		}

		String planCode = null;
		String planName = null;
		List<SaasPlanFeatureResponse> planFeatures = List.of();
		if (subscription.getSaasPlanId() != null) {
			try {
				SaasPlanResponse plan = saasPlanService.getById(subscription.getSaasPlanId());
				planCode = plan.code();
				planName = plan.name();
				if (plan.features() != null) {
					planFeatures = plan.features();
				}
			} catch (ResourceNotFoundException ignored) {
				// el plan fue eliminado después de ser asignado
			}
		}

		return new GymSubscriptionResponse(
				subscription.getId(),
				subscription.getGymId(),
				subscription.getSaasPlanId(),
				planCode,
				planName,
				subscription.getStartDate(),
				subscription.getEndDate(),
				subscription.getStatus(),
				active,
				daysRemaining,
				subscription.getNotes(),
				subscription.getCreatedAt(),
				subscription.getUpdatedAt(),
				planFeatures
		);
	}

	private GymSubscriptionResponse emptyResponse(Long gymId) {
		return new GymSubscriptionResponse(
				null,
				gymId,
				null,
				null,
				null,
				null,
				null,
				false,
				false,
				null,
				null,
				null,
				null,
				List.of()
		);
	}

	private void validateDates(LocalDate startDate, LocalDate endDate) {
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException("Las fechas de inicio y fin son requeridas");
		}
		if (endDate.isBefore(startDate)) {
			throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
		}
	}

	private void ensureGymExists(Long gymId) {
		gymRepository.findById(gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + gymId));
	}
}