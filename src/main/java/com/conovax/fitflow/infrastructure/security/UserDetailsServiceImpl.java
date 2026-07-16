package com.conovax.sexbody.infrastructure.security;

import com.conovax.sexbody.domain.entities.Role;
import com.conovax.sexbody.domain.entities.User;
import com.conovax.sexbody.domain.repositories.RoleRepository;
import com.conovax.sexbody.domain.repositories.UserRepository;
import com.conovax.sexbody.domain.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final RoleRepository roleRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByLoginWithRolesAndPermissions(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

		User enriched = enrichRoles(user);
		return new UserDetailsImpl(enriched);
	}

	private User enrichRoles(User user) {
		if (user == null || user.getId() == null) {
			return user;
		}

		var roleIds = userRoleRepository.findRoleIdsByUserId(user.getId());
		if (roleIds == null || roleIds.isEmpty()) {
			return user;
		}

		var roles = roleRepository.findAllById(roleIds).stream()
				.filter(role -> Boolean.TRUE.equals(role.getStatus()))
				.toList();

		return user.toBuilder()
				.roles(new java.util.HashSet<Role>(roles))
				.build();
	}
}
