package com.conovax.sexbody.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserGymMembershipActiveUser {
	private Long userGymMembershipId;
	private Long userGymId;
	private Long membershipId;
	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean status;
	private Long userId;
	private Long peopleId;
	private String names;
	private String surnames;
	private String email;
	private String phone;
}
