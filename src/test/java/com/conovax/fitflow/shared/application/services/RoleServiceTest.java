package com.conovax.fitflow.shared.application.services;

import com.conovax.fitflow.gym.domain.entities.UsersGym;
import com.conovax.fitflow.gym.domain.repositories.UsersGymRepository;
import com.conovax.fitflow.shared.domain.entities.Role;
import com.conovax.fitflow.shared.domain.repositories.PermissionRepository;
import com.conovax.fitflow.shared.domain.repositories.PermissionRoleRepository;
import com.conovax.fitflow.shared.domain.repositories.RoleRepository;
import com.conovax.fitflow.shared.domain.repositories.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Mock RoleRepository roleRepository;
    @Mock PermissionRepository permissionRepository;
    @Mock PermissionRoleRepository permissionRoleRepository;
    @Mock UsersGymRepository usersGymRepository;
    @Mock UserRoleRepository userRoleRepository;
    @InjectMocks RoleService service;

    @Test
    void activeTrainerOfTheSameGymIsAccepted() {
        stubUserGym(7L, 5L, true, role("Entrenador", true));

        assertTrue(service.isActiveTrainerOrAdminUserGym(7L, 5L));
    }

    @Test
    void activeAdministratorOfTheSameGymIsAccepted() {
        stubUserGym(7L, 5L, true, role("Administrador", true));

        assertTrue(service.isActiveTrainerOrAdminUserGym(7L, 5L));
    }

    @Test
    void otherRolesAreRejected() {
        stubUserGym(7L, 5L, true, role("Recepcionista", true));

        assertFalse(service.isActiveTrainerOrAdminUserGym(7L, 5L));
    }

    @Test
    void inactiveTrainerRoleIsRejected() {
        stubUserGym(7L, 5L, true, role("Entrenador", false));

        assertFalse(service.isActiveTrainerOrAdminUserGym(7L, 5L));
    }

    @Test
    void userGymOfAnotherGymOrInactiveIsRejected() {
        when(usersGymRepository.findByIdAndGymIdAndStatusTrue(5L, 8L)).thenReturn(Optional.empty());

        assertFalse(service.isActiveTrainerOrAdminUserGym(8L, 5L));
        verifyNoInteractions(userRoleRepository, roleRepository);
    }

    @Test
    void nullIdsAreRejected() {
        assertFalse(service.isActiveTrainerOrAdminUserGym(null, 5L));
        assertFalse(service.isActiveTrainerOrAdminUserGym(7L, null));
    }

    private void stubUserGym(Long gymId, Long userGymId, boolean found, Role role) {
        when(usersGymRepository.findByIdAndGymIdAndStatusTrue(userGymId, gymId))
                .thenReturn(found ? Optional.of(UsersGym.builder().id(userGymId).gymId(gymId).status(true).build()) : Optional.empty());
        when(userRoleRepository.findRoleIdsByUserGymId(userGymId)).thenReturn(List.of(1L));
        when(roleRepository.findAllById(List.of(1L))).thenReturn(List.of(role));
    }

    private Role role(String name, boolean active) {
        return Role.builder().id(1L).name(name).status(active).build();
    }
}
