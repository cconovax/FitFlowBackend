package com.conovax.fitflow.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Permission {
	private Long id;
	private String name;
	private String description;
	private Boolean basic;
	private Boolean status;
	private LocalDateTime createdAt;
	@Builder.Default
	private Set<Role> roles = new HashSet<>();
}
