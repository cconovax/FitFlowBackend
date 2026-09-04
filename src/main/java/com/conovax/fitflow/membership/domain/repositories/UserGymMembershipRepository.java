package com.conovax.fitflow.membership.domain.repositories;

import com.conovax.fitflow.membership.domain.entities.UserGymMembershipActiveUser;
import com.conovax.fitflow.membership.domain.entities.UserGymMembership;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserGymMembershipRepository {
	List<UserGymMembership> findAllByUserGymIdOrderByStartDateDescIdDesc(Long userGymId);

	Optional<UserGymMembership> findById(Long id);

	boolean existsActiveMembershipByUserGymId(Long userGymId, LocalDate today);

	boolean existsActiveMembershipByUserGymIdAndMembershipId(Long userGymId, Long membershipId, LocalDate today);

	List<UserGymMembershipActiveUser> findActiveUsersByMembershipId(Long membershipId, LocalDate today);

	UserGymMembership save(UserGymMembership entity);

	long countActiveMembershipsByGymId(Long gymId, LocalDate today);

	long countMembershipsByGymIdAndDateRange(Long gymId, LocalDate from, LocalDate to);

	List<Object[]> findMembershipDistributionByGymId(Long gymId, LocalDate today);
}
