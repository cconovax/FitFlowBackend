package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.UserGymMembershipActiveUser;
import com.conovax.sexbody.domain.entities.UserGymMembership;

import java.time.LocalDate;
import java.util.List;

public interface UserGymMembershipRepository {
	List<UserGymMembership> findAllByUserGymIdOrderByStartDateDescIdDesc(Long userGymId);

	java.util.Optional<UserGymMembership> findById(Long id);

	boolean existsActiveMembershipByUserGymId(Long userGymId, LocalDate today);

	boolean existsActiveMembershipByUserGymIdAndMembershipId(Long userGymId, Long membershipId, LocalDate today);

	List<UserGymMembershipActiveUser> findActiveUsersByMembershipId(Long membershipId, LocalDate today);

	UserGymMembership save(UserGymMembership entity);
}
