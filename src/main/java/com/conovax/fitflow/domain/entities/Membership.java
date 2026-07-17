package com.conovax.fitflow.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership {
	private Long id;
	private String name;
	private String description;
	private Integer durationDay;
	private Boolean status;
	private Long gymId;
}
