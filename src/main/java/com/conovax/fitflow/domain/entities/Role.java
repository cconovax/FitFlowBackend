package com.conovax.sexbody.domain.entities;

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
public class Role {
	private Long id;
	private String name;
	private String code;
	private Long gymId;
	@Builder.Default
	private Boolean fullAccess = false;
	@Builder.Default
	private Boolean isStaff = false;
	@Builder.Default
	private Boolean status = true;
	private String description;
	private LocalDateTime createdAt;
	@Builder.Default
	private Set<User> users = new HashSet<>();
	@Builder.Default
	private Set<Permission> permissions = new HashSet<>();
}
