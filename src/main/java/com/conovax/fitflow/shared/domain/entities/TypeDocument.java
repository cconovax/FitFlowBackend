package com.conovax.fitflow.shared.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeDocument {
	private Long id;
	private String name;
	private String code;
	private Boolean status;
}
