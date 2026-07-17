package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.request.MembershipRequest;
import com.conovax.fitflow.application.dto.response.BenefitResponse;
import com.conovax.fitflow.application.dto.response.MembershipResponse;
import com.conovax.fitflow.application.dto.response.MembershipWithBenefitsResponse;
import com.conovax.fitflow.domain.entities.Benefit;
import com.conovax.fitflow.domain.entities.Membership;
import com.conovax.fitflow.domain.entities.MembershipBenefit;
import com.conovax.fitflow.domain.entities.MembershipPrice;
import com.conovax.fitflow.domain.exceptions.DuplicateResourceException;
import com.conovax.fitflow.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.domain.repositories.BenefitRepository;
import com.conovax.fitflow.domain.repositories.GymRepository;
import com.conovax.fitflow.domain.repositories.MembershipBenefitRepository;
import com.conovax.fitflow.domain.repositories.MembershipPriceRepository;
import com.conovax.fitflow.domain.repositories.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MembershipService {

	private final MembershipRepository repository;
	private final MembershipBenefitRepository membershipBenefitRepository;
	private final BenefitRepository benefitRepository;
	private final MembershipPriceRepository membershipPriceRepository;
	private final GymRepository gymRepository;

	@Transactional(readOnly = true)
	public List<MembershipResponse> getAll() {
		return repository.findAllByStatusTrue().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public List<MembershipWithBenefitsResponse> getAllWithBenefitsAndPrice() {
		List<Membership> memberships = repository.findAllByStatusTrue();
		return toMembershipWithBenefitsResponses(memberships);
	}

	@Transactional(readOnly = true)
	public List<MembershipWithBenefitsResponse> getAllByGymIdWithBenefits(Long gymId) {
		validateGymIfPresent(gymId);

		List<Membership> memberships = repository.findAllByGymIdOrGymIdIsNullAndStatusTrue(gymId);
		return toMembershipWithBenefitsResponses(memberships);
	}

	private List<MembershipWithBenefitsResponse> toMembershipWithBenefitsResponses(List<Membership> memberships) {
		if (memberships == null || memberships.isEmpty()) {
			return List.of();
		}

		List<Long> membershipIds = memberships.stream().map(Membership::getId).toList();
		List<MembershipBenefit> relations = membershipBenefitRepository.findAllByMembershipIdIn(membershipIds);

		List<Long> benefitIds = relations.stream()
				.map(MembershipBenefit::getBenefitId)
				.distinct()
				.toList();

		Map<Long, BenefitResponse> benefitById = new HashMap<>();
		if (!benefitIds.isEmpty()) {
			benefitRepository.findAllByIdInAndStatusTrue(benefitIds)
					.forEach(b -> benefitById.put(b.getId(), toBenefitResponse(b)));
		}

		Map<Long, List<BenefitResponse>> benefitsByMembershipId = new HashMap<>();
		for (MembershipBenefit relation : relations) {
			BenefitResponse benefit = benefitById.get(relation.getBenefitId());
			if (benefit == null) {
				continue;
			}
			benefitsByMembershipId
					.computeIfAbsent(relation.getMembershipId(), ignored -> new ArrayList<>())
					.add(benefit);
		}

		Map<Long, BigDecimal> activePriceByMembershipId = new HashMap<>();
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

		chosenPriceByMembershipId.forEach((membershipId, price) ->
				activePriceByMembershipId.put(membershipId, price.getPrice())
		);

		return memberships.stream()
				.map(m -> new MembershipWithBenefitsResponse(
						m.getId(),
						m.getName(),
						m.getDescription(),
						m.getDurationDay(),
						m.getStatus(),
						m.getGymId(),
						benefitsByMembershipId.getOrDefault(m.getId(), List.of()),
						activePriceByMembershipId.get(m.getId())
				))
				.toList();
	}

	@Transactional(readOnly = true)
	public MembershipResponse getById(Long id) {
		Membership entity = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Membresía no encontrada con ID: " + id));
		return toResponse(entity);
	}

	@Transactional
	public MembershipResponse create(MembershipRequest request) {
		validateGymIfPresent(request.gymId());
		requirePriceOnCreate(request.price());

		if (repository.existsByNameIgnoreCaseAndGymIdAndStatusTrue(request.name(), request.gymId())) {
			throw new DuplicateResourceException("Ya existe una membresía con ese nombre");
		}

		Membership entity = Membership.builder()
				.name(request.name())
				.description(request.description())
				.durationDay(request.durationDay())
				.gymId(request.gymId())
				.status(true)
				.build();

		Membership saved = repository.save(entity);
		createInitialPrice(saved.getId(), request.price());
		persistBenefits(saved.getId(), saved.getGymId(), request.benefitIds());
		return toResponse(saved);
	}

	@Transactional
	public MembershipResponse update(Long id, MembershipRequest request) {
		Membership entity = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Membresía no encontrada con ID: " + id));

		validateGymIfPresent(request.gymId());

		if (repository.existsByNameIgnoreCaseAndGymIdAndIdNotAndStatusTrue(request.name(), request.gymId(), id)) {
			throw new DuplicateResourceException("Ya existe una membresía con ese nombre");
		}

		Membership updated = entity.toBuilder()
				.name(request.name())
				.description(request.description())
				.durationDay(request.durationDay())
				.gymId(request.gymId())
				.build();

		Membership saved = repository.save(updated);
		updatePriceIfProvided(saved.getId(), request.price());
		updateBenefits(saved.getId(), saved.getGymId(), request.benefitIds());
		return toResponse(saved);
	}

	private void requirePriceOnCreate(BigDecimal price) {
		if (price == null) {
			throw new IllegalArgumentException("El precio es obligatorio");
		}
	}

	private void createInitialPrice(Long membershipId, BigDecimal price) {
		if (membershipId == null || price == null) {
			return;
		}

		membershipPriceRepository.save(MembershipPrice.builder()
				.membershipId(membershipId)
				.price(price)
				.startDate(LocalDate.now())
				.endDate(null)
				.build());
	}

	private void updatePriceIfProvided(Long membershipId, BigDecimal newPrice) {
		if (membershipId == null || newPrice == null) {
			return;
		}

		List<MembershipPrice> activePrices = membershipPriceRepository
				.findAllByMembershipIdAndEndDateIsNull(membershipId);

		if (!activePrices.isEmpty()) {
			boolean samePriceAsCurrent = activePrices.stream()
					.anyMatch(p -> p.getPrice() != null && p.getPrice().compareTo(newPrice) == 0);
			if (samePriceAsCurrent) {
				return;
			}

			LocalDate now = LocalDate.now();
			List<MembershipPrice> closedPrices = activePrices.stream()
					.map(p -> p.toBuilder().endDate(now).build())
					.toList();
			membershipPriceRepository.saveAll(closedPrices);
		}

		membershipPriceRepository.save(MembershipPrice.builder()
				.membershipId(membershipId)
				.price(newPrice)
				.startDate(LocalDate.now())
				.endDate(null)
				.build());
	}

	@Transactional
	public void deleteLogical(Long id) {
		Membership entity = repository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Membresía no encontrada con ID: " + id));
		repository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		Membership entity = repository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Membresía no encontrada con ID: " + id));
		repository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Membresía no encontrada con ID: " + id);
		}
		repository.deleteById(id);
	}

	private void validateGymIfPresent(Long gymId) {
		if (gymId == null) {
			return;
		}

		gymRepository.findByIdAndStatusTrue(gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Gym no encontrado con ID: " + gymId));
	}

	private void persistBenefits(Long membershipId, Long membershipGymId, Set<Long> benefitIds) {
		if (membershipId == null || benefitIds == null || benefitIds.isEmpty()) {
			return;
		}

		List<MembershipBenefit> relations = new ArrayList<>();
		for (Long benefitId : benefitIds) {
			if (benefitId == null) {
				continue;
			}

			Benefit benefit = benefitRepository.findByIdAndStatusTrue(benefitId)
					.orElseThrow(() -> new ResourceNotFoundException("Beneficio no encontrado con ID: " + benefitId));

			Long benefitGymId = benefit.getGymId();
			boolean compatible = (membershipGymId == null)
					? benefitGymId == null
					: (benefitGymId == null || membershipGymId.equals(benefitGymId));
			if (!compatible) {
				throw new IllegalArgumentException(
						"El beneficio con ID " + benefitId + " no pertenece al gym de la membresía"
				);
			}

			relations.add(MembershipBenefit.builder()
					.membershipId(membershipId)
					.benefitId(benefitId)
					.build());
		}

		if (!relations.isEmpty()) {
			membershipBenefitRepository.saveAll(relations);
		}
	}

	private void updateBenefits(Long membershipId, Long membershipGymId, Set<Long> benefitIds) {
		if (membershipId == null || benefitIds == null) {
			return;
		}

		membershipBenefitRepository.deleteByMembershipId(membershipId);
		persistBenefits(membershipId, membershipGymId, benefitIds);
	}

	private MembershipResponse toResponse(Membership entity) {
		List<Long> benefitIds = (entity.getId() == null)
				? List.of()
				: membershipBenefitRepository.findAllByMembershipIdIn(List.of(entity.getId())).stream()
						.map(MembershipBenefit::getBenefitId)
						.filter(java.util.Objects::nonNull)
						.distinct()
						.toList();

		return new MembershipResponse(
				entity.getId(),
				entity.getName(),
				entity.getDescription(),
				entity.getDurationDay(),
				entity.getStatus(),
				entity.getGymId(),
				benefitIds
		);
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
}
