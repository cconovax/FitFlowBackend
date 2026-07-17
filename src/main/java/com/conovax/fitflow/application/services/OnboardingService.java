package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.GymSubscribeRequest;
import com.conovax.fitflow.application.dto.request.GymSubscriptionRequest;
import com.conovax.fitflow.application.dto.request.LoginWithGymRequest;
import com.conovax.fitflow.application.dto.request.OnboardingGymRequest;
import com.conovax.fitflow.application.dto.request.OnboardingRegisterRequest;
import com.conovax.fitflow.application.dto.request.RegisterRequest;
import com.conovax.fitflow.application.dto.response.AuthGymResponse;
import com.conovax.fitflow.application.dto.response.OnboardingGymResponse;
import com.conovax.fitflow.application.dto.response.SaasPlanResponse;
import com.conovax.fitflow.domain.entities.Gym;
import com.conovax.fitflow.domain.entities.GymPaymentOrder;
import com.conovax.fitflow.domain.entities.GymStatus;
import com.conovax.fitflow.domain.entities.GymSubscription;
import com.conovax.fitflow.domain.entities.UsersGym;
import com.conovax.fitflow.domain.entities.UsersGymRole;
import com.conovax.fitflow.domain.exceptions.DuplicateResourceException;
import com.conovax.fitflow.domain.repositories.GymPaymentOrderRepository;
import com.conovax.fitflow.domain.repositories.GymRepository;
import com.conovax.fitflow.domain.repositories.GymSubscriptionRepository;
import com.conovax.fitflow.domain.repositories.MunicipalityRepository;
import com.conovax.fitflow.domain.repositories.RoleRepository;
import com.conovax.fitflow.domain.repositories.UserRoleRepository;
import com.conovax.fitflow.domain.repositories.UsersGymRepository;
import com.conovax.fitflow.infrastructure.payment.StripeService;
import com.conovax.fitflow.infrastructure.security.UserDetailsImpl;
import com.conovax.fitflow.infrastructure.storage.FileStorageService;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingService {

	private static final Logger log = LoggerFactory.getLogger(OnboardingService.class);
	private static final String ADMIN_ROLE_NAME = "Administrador";

	private final GymRepository gymRepository;
	private final MunicipalityRepository municipalityRepository;
	private final UsersGymRepository usersGymRepository;
	private final FileStorageService fileStorageService;
	private final AuthService authService;
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;
	private final GymSubscriptionRepository gymSubscriptionRepository;
	private final GymSubscriptionService gymSubscriptionService;
	private final GymSaasSubscriptionService gymSaasSubscriptionService;
	private final GymPaymentOrderRepository gymPaymentOrderRepository;
	private final SaasPlanService saasPlanService;
	private final StripeService stripeService;

	/**
	 * Endpoint público: registra al usuario, crea el gym en TRIAL y devuelve el JWT listo.
	 */
	@Transactional
	public AuthGymResponse selfRegister(OnboardingRegisterRequest request, MultipartFile logo) {
		// 1. Registrar usuario
		RegisterRequest userReq = new RegisterRequest(
				request.email(),
				request.names(),
				request.surnames(),
				null,
				null,
				request.numDocument(),
				request.municipalitieId(),
				request.sexoId(),
				request.typeDocumentId(),
				request.password(),
				null
		);
		authService.register(userReq);

		// 2. Crear gimnasio TRIAL
		String logoUrl = "";
		if (logo != null && !logo.isEmpty()) {
			logoUrl = fileStorageService.storeGymLogo(logo);
		}

		Gym gym = Gym.builder()
				.name(request.gymName())
				.nit(request.gymNit())
				.logo(logoUrl)
				.municipalitieId(request.gymMunicipalitieId())
				.status(true)
				.phone(request.gymPhone())
				.email(request.gymEmail())
				.gymStatus(GymStatus.TRIAL)
				.trialExpiresAt(LocalDateTime.now().plusDays(14))
				.build();

		Gym savedGym = gymRepository.save(gym);

		// 3. Buscar el userId recién creado (por email, ya que es el campo único de login)
		// Usamos fetchUserGyms → no, usamos findByLogin del auth internamente
		// Reusamos loginWithGym para obtener el userId via authenticate
		// Para hacerlo simple: delegamos todo al loginWithGym después de crear la relación

		// 3a. Necesitamos el userId → obtenemos via authenticate
		String loginKey = request.email();
		String rawPassword = request.password();

		// Autenticamos para obtener userDetails y el userId
		org.springframework.security.authentication.UsernamePasswordAuthenticationToken tempAuth =
				new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(loginKey, rawPassword);

		org.springframework.security.core.Authentication authentication =
				authService.getAuthenticationManager().authenticate(tempAuth);

		UserDetailsImpl userDetails =
				(UserDetailsImpl) authentication.getPrincipal();
		Long userId = userDetails.getUser().getId();

		// 3b. Crear relación usuario-gym
		UsersGym relation = UsersGym.builder()
				.userId(userId)
				.gymId(savedGym.getId())
				.fingerprint(new byte[0])
				.status(true)
				.build();
		UsersGym savedRelation = usersGymRepository.save(relation);

		// 3c. Asignar rol Administrador al nuevo usuario en este gym
		roleRepository.findByName(ADMIN_ROLE_NAME).ifPresentOrElse(
				adminRole -> userRoleRepository.save(
						UsersGymRole.builder()
								.userGymId(savedRelation.getId())
								.roleId(adminRole.getId())
								.build()),
				() -> log.warn("Rol '{}' no encontrado — el usuario no tendrá rol asignado.", ADMIN_ROLE_NAME)
		);

		// 3d. Suscripción: plan seleccionado con Stripe, o prueba de 14 días
		handleOnboardingSubscription(savedGym.getId(), userId, request);

		// 4. Login completo con gymId → devuelve JWT
		LoginWithGymRequest loginReq = new LoginWithGymRequest(loginKey, rawPassword, savedRelation.getId());
		return authService.loginWithGym(loginReq);
	}

	// ── Subscription logic ─────────────────────────────────────────────────────

	/**
	 * Maneja la suscripción del gym durante el onboarding:
	 * - Plan pagado: crea la suscripción en Stripe (GymPaymentOrder PENDING, activa por webhook).
	 * - Plan gratuito (precio=0): crea el registro de suscripción directamente, guarda tarjeta sin cobrar.
	 * - Sin plan: crea una prueba gratuita de 14 días.
	 * Antes de activar un plan gratuito, valida que el usuario no lo haya adquirido antes.
	 */
	private void handleOnboardingSubscription(Long gymId, Long userId, OnboardingRegisterRequest request) {
		if (request.saasPlanId() == null) {
			// Sin plan seleccionado → período de prueba 14 días
			gymSubscriptionRepository.save(GymSubscription.builder()
					.gymId(gymId)
					.saasPlanId(null)
					.startDate(LocalDate.now())
					.endDate(LocalDate.now().plusDays(14))
					.status(true)
					.notes("Período de prueba gratuito — 14 días")
					.build());
			return;
		}

		SaasPlanResponse plan = saasPlanService.getById(request.saasPlanId());
		boolean isFree = plan.price() == null || plan.price().compareTo(BigDecimal.ZERO) == 0;

		if (isFree) {
			// Validar que el usuario no haya usado este plan gratuito en otro gimnasio
			validateNoFreePlanAbuse(userId, request.saasPlanId());

			// Crear registro de suscripción directo (sin cobro Stripe)
			gymSubscriptionService.create(gymId, new GymSubscriptionRequest(
					request.saasPlanId(),
					LocalDate.now(),
					LocalDate.now().plusMonths(1),
					Boolean.TRUE,
					"Plan gratuito activado en onboarding"
			));

			// Guardar tarjeta en Stripe sin cobrar + persistir stripeCustomerId para gestión posterior
			if (request.paymentMethodId() != null && !request.paymentMethodId().isBlank()) {
				try {
					String existingCustomerId = gymPaymentOrderRepository.findLatestOrderByGymId(gymId)
							.map(GymPaymentOrder::getStripeCustomerId)
							.filter(id -> id != null && !id.isBlank())
							.orElse(null);
					String customerId = stripeService.getOrCreateGymCustomer(gymId, existingCustomerId, request.email());
					stripeService.attachPaymentMethod(customerId, request.paymentMethodId());
					// Persistir el stripeCustomerId en una GymPaymentOrder con status FREE
					// para que listPaymentMethods() pueda recuperar las tarjetas del gym después.
					gymPaymentOrderRepository.save(GymPaymentOrder.builder()
							.gymId(gymId)
							.saasPlanId(request.saasPlanId())
							.stripeCustomerId(customerId)
							.stripeSubscriptionId(null)
							.paymentGateway("STRIPE")
							.paymentMethodType("CARD")
							.amountInCents(0L)
							.currency("COP")
							.status("FREE")
							.customerEmail(request.email())
							.build());
					log.info("Tarjeta guardada en Stripe para gym {} — customerId {} persistido", gymId, customerId);
				} catch (Exception ex) {
					log.warn("No se pudo guardar la tarjeta en Stripe para gym {}: {}", gymId, ex.getMessage());
				}
			}

		} else {
			// Plan de pago: se requiere método de pago
			if (request.paymentMethodId() == null || request.paymentMethodId().isBlank()) {
				throw new IllegalArgumentException(
						"Se requiere un método de pago para el plan '" + plan.name() + "'.");
			}
			if (plan.stripePriceId() == null || plan.stripePriceId().isBlank()) {
				throw new IllegalStateException(
						"El plan '" + plan.code() + "' no tiene un Price ID de Stripe configurado. Contacta al administrador.");
			}
			// Crear suscripción recurrente en Stripe → orden queda PENDING hasta confirmación por webhook
			gymSaasSubscriptionService.subscribe(gymId, new GymSubscribeRequest(
					request.saasPlanId(),
					"CARD",
					request.paymentMethodId(),
					request.email()
			));
		}
	}

	/**
	 * Impide que un usuario adquiera el plan gratuito más de una vez (en distintos gimnasios).
	 */
	private void validateNoFreePlanAbuse(Long userId, Long saasPlanId) {
		List<UsersGym> userGyms = usersGymRepository.findAllByUserId(userId);
		boolean alreadyUsed = userGyms.stream()
				.anyMatch(ug -> gymSubscriptionRepository.existsByGymIdAndSaasPlanId(ug.getGymId(), saasPlanId));
		if (alreadyUsed) {
			throw new IllegalStateException(
					"Ya has utilizado el plan gratuito en un gimnasio anterior. No es posible adquirirlo nuevamente.");
		}
	}

	@Transactional
	public OnboardingGymResponse createOwnGym(OnboardingGymRequest request, MultipartFile logo) {
		Long userId = resolveCurrentUserId();

		municipalityRepository.findByIdAndStatusTrue(request.municipalitieId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Municipio no encontrado con ID: " + request.municipalitieId()));

		String logoUrl = "";
		if (logo != null && !logo.isEmpty()) {
			logoUrl = fileStorageService.storeGymLogo(logo);
		}

		LocalDateTime trialExpiresAt = LocalDateTime.now().plusDays(14);

		Gym gym = Gym.builder()
				.name(request.name())
				.nit(request.nit())
				.logo(logoUrl)
				.municipalitieId(request.municipalitieId())
				.status(true)
				.phone(request.phone())
				.email(request.email())
				.gymStatus(GymStatus.TRIAL)
				.trialExpiresAt(trialExpiresAt)
				.build();

		Gym saved = gymRepository.save(gym);

		if (usersGymRepository.existsByUserIdAndGymIdAndStatusTrue(userId, saved.getId())) {
			throw new DuplicateResourceException("El usuario ya está registrado en este gym");
		}

		UsersGym relation = UsersGym.builder()
				.userId(userId)
				.gymId(saved.getId())
				.fingerprint(new byte[0])
				.status(true)
				.build();

		UsersGym savedRelation = usersGymRepository.save(relation);

		// Asignar rol Administrador
		roleRepository.findByName(ADMIN_ROLE_NAME).ifPresentOrElse(
				adminRole -> userRoleRepository.save(
						UsersGymRole.builder()
								.userGymId(savedRelation.getId())
								.roleId(adminRole.getId())
								.build()),
				() -> log.warn("Rol '{}' no encontrado — el usuario no tendrá rol asignado.", ADMIN_ROLE_NAME)
		);

		// Crear suscripción de prueba 14 días
		gymSubscriptionRepository.save(GymSubscription.builder()
				.gymId(saved.getId())
				.saasPlanId(null)
				.startDate(LocalDate.now())
				.endDate(LocalDate.now().plusDays(14))
				.status(true)
				.notes("Período de prueba gratuito — 14 días")
				.build());

		return new OnboardingGymResponse(
				saved.getId(),
				saved.getName(),
				saved.getGymStatus() != null ? saved.getGymStatus().name() : null,
				saved.getTrialExpiresAt(),
				savedRelation.getId()
		);
	}

	private Long resolveCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated()) {
			throw new IllegalStateException("No hay sesión activa");
		}
		Object principal = auth.getPrincipal();
		if (principal instanceof UserDetailsImpl ud) {
			return ud.getUser().getId();
		}
		throw new IllegalStateException("No se pudo obtener el usuario de la sesión");
	}
}
