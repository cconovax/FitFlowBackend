package com.conovax.fitflow.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Gym {
	private Long id;
	private String name;
	private String nit;
	private String logo;
	private Long municipalitieId;
	private Boolean status;
	private String phone;
	private String email;
	/** null = gym principal / raíz; valor = ID del gym al que pertenece como sede */
	private Long parentGymId;
	private GymStatus gymStatus;
	private LocalDateTime trialExpiresAt;
}
