package com.conovax.fitflow.membership.application.services;

import com.conovax.fitflow.membership.application.dto.response.BenefitResponse;
import com.conovax.fitflow.membership.application.dto.response.MembershipWithBenefitsResponse;
import com.conovax.fitflow.membership.application.dto.response.UserGymMembershipDetailResponse;
import com.conovax.fitflow.membership.application.dto.response.UserGymMembershipResponse;
import com.conovax.fitflow.membership.domain.entities.Benefit;
import com.conovax.fitflow.membership.domain.entities.Membership;
import com.conovax.fitflow.membership.domain.entities.MembershipBenefit;
import com.conovax.fitflow.membership.domain.entities.MembershipPrice;
import com.conovax.fitflow.membership.domain.entities.UserGymMembership;
import com.conovax.fitflow.gym.domain.entities.UsersGym;
import com.conovax.fitflow.shared.domain.entities.User;
import com.conovax.fitflow.shared.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.membership.domain.repositories.BenefitRepository;
import com.conovax.fitflow.membership.domain.repositories.MembershipBenefitRepository;
import com.conovax.fitflow.membership.domain.repositories.MembershipPriceRepository;
import com.conovax.fitflow.membership.domain.repositories.MembershipRepository;
import com.conovax.fitflow.notifications.application.services.NotificationService;
import com.conovax.fitflow.shared.domain.repositories.RoleRepository;
import com.conovax.fitflow.membership.domain.repositories.UserGymMembershipRepository;
import com.conovax.fitflow.shared.domain.repositories.UserRepository;
import com.conovax.fitflow.shared.domain.repositories.UserRoleRepository;
import com.conovax.fitflow.gym.domain.repositories.UsersGymRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserGymMembershipService {

	private final UserGymMembershipRepository repository;
	private final UsersGymRepository usersGymRepository;
	private final MembershipRepository membershipRepository;
	private final MembershipPriceRepository membershipPriceRepository;
	private final MembershipBenefitRepository membershipBenefitRepository;
	private final BenefitRepository benefitRepository;
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;
	private final UserRepository userRepository;
	private final NotificationService notificationService;

	@Transactional(readOnly = true)
	public List<UserGymMembershipDetailResponse> getAllByUserGymIdAndGymId(Long userGymId, Long gymId) {
		UsersGym userGym = usersGymRepository.findByIdAndStatusTrue(userGymId)
				.orElseThrow(() -> new ResourceNotFoundException("No tienes una membresia relacionada a este gimnasio"));

		if (gymId == null) {
			throw new IllegalArgumentException("gymId es obligatorio");
		}
		if (userGym.getGymId() == null || !gymId.equals(userGym.getGymId())) {
			throw new IllegalArgumentException("El userGymId no pertenece al gym indicado");
		}

		List<UserGymMembership> relations = repository.findAllByUserGymIdOrderByStartDateDescIdDesc(userGymId);
		if (relations.isEmpty()) {
			return List.of();
		}

		List<Long> membershipIds = relations.stream()
				.map(UserGymMembership::getMembershipId)
				.filter(id -> id != null)
				.distinct()
				.toList();

		Map<Long, Membership> membershipById = membershipRepository.findAllById(membershipIds).stream()
				.collect(Collectors.toMap(Membership::getId, m -> m, (a, b) -> a));

		Map<Long, List<BenefitResponse>> benefitsByMembershipId = loadBenefitsByMembershipId(membershipIds);
		Map<Long, BigDecimal> activePriceByMembershipId = loadActivePriceByMembershipId(membershipIds);

		return relations.stream()
				.map(ugm -> {
					Long membershipId = ugm.getMembershipId();
					Membership membership = membershipId == null ? null : membershipById.get(membershipId);

					MembershipWithBenefitsResponse membershipResponse = (membership == null) ? null : new MembershipWithBenefitsResponse(
							membership.getId(),
							membership.getName(),
							membership.getDescription(),
							membership.getDurationDay(),
							membership.getStatus(),
							membership.getGymId(),
							benefitsByMembershipId.getOrDefault(membershipId, List.of()),
							activePriceByMembershipId.get(membershipId)
					);

					return new UserGymMembershipDetailResponse(
							ugm.getId(),
							ugm.getUserGymId(),
							ugm.getMembershipId(),
							ugm.getPaymentPreci(),
							ugm.getStartDate(),
							ugm.getEndDate(),
							ugm.getStatus(),
							membershipResponse
					);
				})
				.toList();
	}

	@Transactional
	public UserGymMembershipResponse assignMembershipToUserGym(Long userGymId, Long membershipId) {
		UsersGym userGym = usersGymRepository.findByIdAndStatusTrue(userGymId)
				.orElseThrow(() -> new ResourceNotFoundException("No tienes una membresia relacionada a este gimnasio"));

		assertIsRegularMember(userGymId);

		Membership membership = membershipRepository.findByIdAndStatusTrue(membershipId)
				.orElseThrow(() -> new ResourceNotFoundException("Membresía no encontrada"));

		Long membershipGymId = membership.getGymId();
		if (membershipGymId != null && !membershipGymId.equals(userGym.getGymId())) {
			throw new IllegalArgumentException("La membresía no pertenece al gimnasio del usuario");
		}

		MembershipPrice activePrice = membershipPriceRepository
				.findAllByMembershipIdAndEndDateIsNull(membershipId)
				.stream()
				.max(Comparator
						.comparing(MembershipPrice::getStartDate, Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparing(MembershipPrice::getId, Comparator.nullsLast(Comparator.naturalOrder()))
				)
				.orElseThrow(() -> new ResourceNotFoundException(
						"No existe un precio activo para la membresía con ID: " + membershipId
				));

		if (membership.getDurationDay() == null || membership.getDurationDay() <= 0) {
			throw new IllegalArgumentException("La membresía no tiene una duración válida");
		}

		if (repository.existsActiveMembershipByUserGymIdAndMembershipId(userGymId, membershipId, LocalDate.now())) {
			throw new IllegalArgumentException("El usuario ya tiene esta membresía activa.");
		}

		LocalDate startDate = LocalDate.now();
		LocalDate endDate = startDate.plusDays(membership.getDurationDay().longValue());

		UserGymMembership saved = repository.save(UserGymMembership.builder()
				.userGymId(userGymId)
				.membershipId(membershipId)
				.paymentPreci(activePrice.getPrice())
				.startDate(startDate)
				.endDate(endDate)
				.status(true)
				.build());

		sendMembershipActivatedNotification(userGym, membership, activePrice.getPrice(), startDate, endDate);

		return toResponse(saved);
	}

	/** Notifica al usuario que su membresía fue activada. Un fallo de envío no revierte la asignación. */
	private void sendMembershipActivatedNotification(
			UsersGym userGym, Membership membership, BigDecimal price, LocalDate startDate, LocalDate endDate
	) {
		try {
			User user = userRepository.findById(userGym.getUserId()).orElse(null);
			if (user == null || user.getPeople() == null) return;

			String email = user.getPeople().getEmail();
			if (email == null || email.isBlank()) return;

			List<String> benefitNames = loadBenefitsByMembershipId(List.of(membership.getId()))
					.getOrDefault(membership.getId(), List.of())
					.stream()
					.map(BenefitResponse::name)
					.toList();

			notificationService.sendEmail(email, "membership_activated", Map.of(
					"userName", user.getPeople().getNames(),
					"membershipName", membership.getName(),
					"price", formatCurrency(price),
					"startDate", startDate.toString(),
					"endDate", endDate.toString(),
					"benefits", benefitNames
			));
		} catch (Exception e) {
			log.warn("No se pudo enviar el email de membresía activada para userGymId {}: {}",
					userGym.getId(), e.getMessage());
		}
	}

	private String formatCurrency(BigDecimal amount) {
		if (amount == null) return "$0";
		NumberFormat format = NumberFormat.getNumberInstance(new Locale("es", "CO"));
		format.setMaximumFractionDigits(0);
		return "$" + format.format(amount);
	}

	private Map<Long, List<BenefitResponse>> loadBenefitsByMembershipId(List<Long> membershipIds) {
		if (membershipIds == null || membershipIds.isEmpty()) {
			return Map.of();
		}

		List<MembershipBenefit> relations = membershipBenefitRepository.findAllByMembershipIdIn(membershipIds);
		List<Long> benefitIds = relations.stream()
				.map(MembershipBenefit::getBenefitId)
				.filter(id -> id != null)
				.distinct()
				.toList();

		Map<Long, BenefitResponse> benefitById = new HashMap<>();
		if (!benefitIds.isEmpty()) {
			benefitRepository.findAllByIdInAndStatusTrue(benefitIds)
					.forEach(b -> benefitById.put(b.getId(), toBenefitResponse(b)));
		}

		Map<Long, List<BenefitResponse>> benefitsByMembershipId = new HashMap<>();
		for (MembershipBenefit relation : relations) {
			if (relation.getMembershipId() == null || relation.getBenefitId() == null) {
				continue;
			}
			BenefitResponse benefit = benefitById.get(relation.getBenefitId());
			if (benefit == null) {
				continue;
			}
			benefitsByMembershipId
					.computeIfAbsent(relation.getMembershipId(), ignored -> new ArrayList<>())
					.add(benefit);
		}

		return benefitsByMembershipId;
	}

	private Map<Long, BigDecimal> loadActivePriceByMembershipId(List<Long> membershipIds) {
		if (membershipIds == null || membershipIds.isEmpty()) {
			return Map.of();
		}

		List<MembershipPrice> activePrices = membershipPriceRepository
				.findAllByMembershipIdInAndEndDateIsNull(membershipIds);

		Map<Long, MembershipPrice> chosenPriceByMembershipId = new HashMap<>();
		for (MembershipPrice price : activePrices) {
			if (price.getMembershipId() == null || price.getPrice() == null) {
				continue;
			}

			MembershipPrice chosen = chosenPriceByMembershipId.get(price.getMembershipId());
			if (chosen == null) {
				chosenPriceByMembershipId.put(price.getMembershipId(), price);
				continue;
			}

			if (price.getStartDate() != null && chosen.getStartDate() != null) {
				int cmp = price.getStartDate().compareTo(chosen.getStartDate());
				if (cmp > 0) {
					chosenPriceByMembershipId.put(price.getMembershipId(), price);
					continue;
				}
				if (cmp < 0) {
					continue;
				}
			}

			if (price.getId() != null && chosen.getId() != null && price.getId() > chosen.getId()) {
				chosenPriceByMembershipId.put(price.getMembershipId(), price);
			}
		}

		Map<Long, BigDecimal> activePriceByMembershipId = new HashMap<>();
		chosenPriceByMembershipId.forEach((membershipId, price) ->
				activePriceByMembershipId.put(membershipId, price.getPrice())
		);

		return activePriceByMembershipId;
	}

	private BenefitResponse toBenefitResponse(Benefit entity) {
		return new BenefitResponse(
				entity.getId(),
				entity.getName(),
				entity.getDescription(),
				entity.getStatus(),
				entity.getGymId()
		);
	}

	private UserGymMembershipResponse toResponse(UserGymMembership entity) {
		return new UserGymMembershipResponse(
				entity.getId(),
				entity.getUserGymId(),
				entity.getMembershipId(),
				entity.getPaymentPreci(),
				entity.getStartDate(),
				entity.getEndDate(),
				entity.getStatus()
		);
	}

	private void assertIsRegularMember(Long userGymId) {
		List<Long> roleIds = userRoleRepository.findRoleIdsByUserGymId(userGymId);
		if (roleIds.isEmpty()) return;

		boolean hasPrivilegedRole = roleRepository.findAllById(roleIds).stream()
				.anyMatch(role -> Boolean.TRUE.equals(role.getFullAccess())
						|| Boolean.TRUE.equals(role.getIsStaff()));

		if (hasPrivilegedRole) {
			throw new IllegalArgumentException(
					"No se puede asignar una membresía a un entrenador o administrador"
			);
		}
	}
}
