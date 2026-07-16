package com.conovax.sexbody.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UsersGym {
	private Long id;
	private Long userId;
	private Long gymId;
	private byte[] fingerprint;
	private Boolean status;
}
