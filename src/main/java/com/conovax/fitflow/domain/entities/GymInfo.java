package com.conovax.fitflow.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GymInfo {
	private Long userGymId;
	private Long id;
	private String name;
	private String logo;
	private String municipalitie;
}
