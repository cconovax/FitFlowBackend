package com.conovax.sexbody.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Municipality {
	private Long id;
	private Long departamentId;
	private String name;
	private Boolean status;
}
