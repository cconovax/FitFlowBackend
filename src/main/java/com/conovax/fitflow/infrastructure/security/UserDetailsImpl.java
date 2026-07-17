package com.conovax.fitflow.infrastructure.security;

import com.conovax.fitflow.domain.entities.Permission;
import com.conovax.fitflow.domain.entities.Role;
import com.conovax.fitflow.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

	private final User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getPermissions().stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		if (user.getPeople() != null) {
			String email = user.getPeople().getEmail();
			if (email != null && !email.isBlank()) {
				return email;
			}
			return user.getPeople().getNumDocument();
		}
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getPeople() == null || Boolean.TRUE.equals(user.getPeople().getStatus());
	}

	public User getUser() {
		return user;
	}

	public Set<String> getRoles() {
		if (user.getRoles() == null) {
			return Set.of();
		}
		return user.getRoles().stream()
				.filter(role -> Boolean.TRUE.equals(role.getStatus()))
				.map(Role::getName)
				.filter(name -> name != null && !name.isBlank())
				.collect(Collectors.toSet());
	}

	public Set<String> getPermissions() {
		Set<String> permissions = new HashSet<>();
		if (user.getRoles() == null) {
			return permissions;
		}

		for (Role role : user.getRoles()) {
			if (!Boolean.TRUE.equals(role.getStatus())) {
				continue;
			}
			if (Boolean.TRUE.equals(role.getFullAccess())) {
				permissions.add("FULL_ACCESS");
			}
			if (role.getPermissions() == null) {
				continue;
			}
			for (Permission permission : role.getPermissions()) {
				if (!Boolean.TRUE.equals(permission.getStatus())) {
					continue;
				}
				String name = permission.getName();
				if (name != null && !name.isBlank()) {
					permissions.add(name);
				}
			}
		}

		return permissions;
	}
}
