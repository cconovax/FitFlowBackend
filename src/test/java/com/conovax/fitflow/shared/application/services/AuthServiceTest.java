package com.conovax.fitflow.shared.application.services;

import com.conovax.fitflow.shared.application.dto.request.LoginWithGymRequest;
import com.conovax.fitflow.shared.application.dto.request.RegisterRequest;
import com.conovax.fitflow.subscription.application.dto.response.GymSubscriptionResponse;
import com.conovax.fitflow.shared.application.dto.response.UserGymPermissionsResponse;
import com.conovax.fitflow.shared.domain.entities.People;
import com.conovax.fitflow.shared.domain.entities.User;
import com.conovax.fitflow.gym.domain.entities.UsersGym;
import com.conovax.fitflow.shared.domain.exceptions.DuplicateResourceException;
import com.conovax.fitflow.shared.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.shared.domain.repositories.MunicipalityRepository;
import com.conovax.fitflow.shared.domain.repositories.SexoRepository;
import com.conovax.fitflow.shared.domain.repositories.TypeDocumentRepository;
import com.conovax.fitflow.shared.domain.repositories.UserRepository;
import com.conovax.fitflow.gym.domain.repositories.UsersGymRepository;
import com.conovax.fitflow.notifications.application.services.NotificationService;
import com.conovax.fitflow.subscription.application.services.GymSubscriptionService;
import com.conovax.fitflow.shared.infrastructure.security.GymAuthenticationDetails;
import com.conovax.fitflow.shared.infrastructure.security.UserDetailsImpl;
import com.conovax.fitflow.shared.infrastructure.security.jwt.JwtUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private static final long USER_ID = 11L;
    private static final long USER_GYM_ID = 21L;
    private static final long GYM_ID = 31L;

    @Mock private UserRepository userRepository;
    @Mock private NotificationService notificationService;
    @Mock private MunicipalityRepository municipalityRepository;
    @Mock private SexoRepository sexoRepository;
    @Mock private TypeDocumentRepository typeDocumentRepository;
    @Mock private UsersGymRepository usersGymRepository;
    @Mock private RoleService roleService;
    @Mock private GymSubscriptionService gymSubscriptionService;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtUtils jwtUtils;
    @InjectMocks private AuthService authService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void registerRejectsDuplicateDocumentBeforeAnyPersistence() {
        RegisterRequest request = registerRequest();
        when(userRepository.existsByNumDocument(request.numDocument())).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("número de documento");

        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void registerCreatesEnabledUserWithDefaultPhotoAndEncodedPassword() {
        RegisterRequest request = registerRequest();
        ReflectionTestUtils.setField(authService, "DEFAULT_PROFILE_PHOTO_URL", "https://cdn.test/default.png");
        ReflectionTestUtils.setField(authService, "URL_FRONTEND_LOGIN", "https://app.test");
        when(userRepository.existsByNumDocument(request.numDocument())).thenReturn(false);
        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(municipalityRepository.findByIdAndStatusTrue(request.municipalitieId())).thenReturn(Optional.of(mock(com.conovax.fitflow.shared.domain.entities.Municipality.class)));
        when(sexoRepository.findByIdAndStatusTrue(request.sexoId())).thenReturn(Optional.of(mock(com.conovax.fitflow.shared.domain.entities.Sexo.class)));
        when(typeDocumentRepository.findByIdAndStatusTrue(request.typeDocumentId())).thenReturn(Optional.of(mock(com.conovax.fitflow.shared.domain.entities.TypeDocument.class)));
        when(passwordEncoder.encode(request.password())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0, User.class).toBuilder().id(USER_ID).build());

        var response = authService.register(request);

        ArgumentCaptor<User> savedUser = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(savedUser.capture());
        assertThat(savedUser.getValue().getPassword()).isEqualTo("encoded-password");
        assertThat(savedUser.getValue().getPeople())
                .extracting(People::getPhoto, People::getStatus, People::getEmail)
                .containsExactly("https://cdn.test/default.png", true, request.email());
        assertThat(response.id()).isEqualTo(USER_ID);
        assertThat(response.email()).isEqualTo(request.email());
        verify(notificationService).sendEmail(eq(request.email()), eq("welcome"), any());
    }

    @Test
    void loginWithGymScopesTokenToAuthenticatedUsersActiveGym() {
        User user = user();
        UsersGym userGym = UsersGym.builder().id(USER_GYM_ID).userId(USER_ID).gymId(GYM_ID).status(true).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(new UserDetailsImpl(user), null);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(usersGymRepository.findByIdAndStatusTrue(USER_GYM_ID)).thenReturn(Optional.of(userGym));
        when(roleService.getRolesAndPermissionsForUserGym(USER_GYM_ID))
                .thenReturn(new UserGymPermissionsResponse(List.of(), List.of("SALES_READ")));
        when(gymSubscriptionService.getCurrentByGymId(GYM_ID))
                .thenReturn(subscription());
        when(userRepository.findGymsByUserId(USER_ID)).thenReturn(List.of());
        when(jwtUtils.generateJwtToken(any(Authentication.class), eq(GYM_ID), eq(USER_GYM_ID))).thenReturn("jwt-token");

        var response = authService.loginWithGym(new LoginWithGymRequest("ana@example.test", "secret", USER_GYM_ID));

        assertThat(response.token()).isEqualTo("jwt-token");
        assertThat(response.id()).isEqualTo(USER_ID);
        assertThat(response.permissions()).containsExactly("SALES_READ");
        Authentication scopedAuthentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(scopedAuthentication.getAuthorities()).extracting("authority").containsExactly("SALES_READ");
        assertThat(scopedAuthentication.getDetails()).isEqualTo(new GymAuthenticationDetails(GYM_ID, USER_GYM_ID));
        verify(jwtUtils).generateJwtToken(scopedAuthentication, GYM_ID, USER_GYM_ID);
    }

    @Test
    void loginWithGymRejectsGymRelationshipOwnedByAnotherUser() {
        User user = user();
        UsersGym otherUsersGym = UsersGym.builder().id(USER_GYM_ID).userId(999L).gymId(GYM_ID).status(true).build();
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(new UserDetailsImpl(user), null));
        when(usersGymRepository.findByIdAndStatusTrue(USER_GYM_ID)).thenReturn(Optional.of(otherUsersGym));

        assertThatThrownBy(() -> authService.loginWithGym(new LoginWithGymRequest("ana@example.test", "secret", USER_GYM_ID)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("no valida");

        verify(jwtUtils, never()).generateJwtToken(any(), any(), any());
    }

    private RegisterRequest registerRequest() {
        return new RegisterRequest("ana@example.test", "Ana", "Pérez", null, null, "10203040", 1L, 2L, 3L, "secret", null);
    }

    private User user() {
        return User.builder()
                .id(USER_ID)
                .password("encoded-password")
                .people(People.builder().names("Ana").email("ana@example.test").numDocument("10203040").status(true).build())
                .build();
    }

    private GymSubscriptionResponse subscription() {
        return new GymSubscriptionResponse(1L, GYM_ID, null, null, null, LocalDate.now(), LocalDate.now().plusDays(14), true, true, 14L, null, null, null, List.of());
    }
}
