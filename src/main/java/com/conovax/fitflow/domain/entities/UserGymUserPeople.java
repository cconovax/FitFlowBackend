package com.conovax.sexbody.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserGymUserPeople {
	private Long userGymId;
	private Long userId;
	private Boolean userGymStatus;
	private Long peopleId;
	private String numDocument;
	private String email;
	private String names;
	private String surnames;
	private String phone;
}
