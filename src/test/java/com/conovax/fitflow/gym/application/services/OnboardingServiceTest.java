package com.conovax.fitflow.gym.application.services;

import com.conovax.fitflow.gym.application.dto.request.OnboardingGymRequest;
import com.conovax.fitflow.gym.application.dto.request.OnboardingRegisterRequest;
import com.conovax.fitflow.shared.application.dto.response.AuthGymResponse;
import com.conovax.fitflow.gym.domain.entities.Gym;
import com.conovax.fitflow.subscription.domain.entities.GymSubscription;
import com.conovax.fitflow.shared.domain.entities.People;
import com.conovax.fitflow.shared.domain.entities.User;
import com.conovax.fitflow.gym.domain.entities.UsersGym;
import com.conovax.fitflow.shared.domain.exceptions.ResourceNotFoundException;
import com.conovax.fitflow.subscription.domain.repositories.GymPaymentOrderRepository;
import com.conovax.fitflow.gym.domain.repositories.GymRepository;
import com.conovax.fitflow.subscription.domain.repositories.GymSubscriptionRepository;
import com.conovax.fitflow.shared.domain.repositories.MunicipalityRepository;
import com.conovax.fitflow.shared.domain.repositories.RoleRepository;
import com.conovax.fitflow.shared.domain.repositories.UserRoleRepository;
import com.conovax.fitflow.gym.domain.repositories.UsersGymRepository;
import com.conovax.fitflow.subscription.application.services.GymSaasSubscriptionService;
import com.conovax.fitflow.subscription.application.services.GymSubscriptionService;
import com.conovax.fitflow.subscription.application.services.SaasPlanService;
import com.conovax.fitflow.subscription.infrastructure.payment.StripeService;
import com.conovax.fitflow.shared.application.services.AuthService;
import com.conovax.fitflow.shared.infrastructure.security.UserDetailsImpl;
import com.conovax.fitflow.shared.infrastructure.storage.FileStorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OnboardingServiceTest {

    private static final long USER_ID = 7L;
    private static final long GYM_ID = 8L;
    private static final long USER_GYM_ID = 9L;

    @Mock private GymRepository gymRepository;
    @Mock private MunicipalityRepository municipalityRepository;
    @Mock private UsersGymRepository usersGymRepository;
    @Mock private FileStorageService fileStorageService;
    @Mock private AuthService authService;
    @Mock private RoleRepository roleRepository;
    @Mock private UserRoleRepository userRoleRepository;
    @Mock private GymSubscriptionRepository gymSubscriptionRepository;
    @Mock private GymSubscriptionService gymSubscriptionService;
    @Mock private GymSaasSubscriptionService gymSaasSubscriptionService;
    @Mock private GymPaymentOrderRepository gymPaymentOrderRepository;
    @Mock private SaasPlanService saasPlanService;
    @Mock private StripeService stripeService;
    @Mock private AuthenticationManager authenticationManager;
    @InjectMocks private OnboardingService onboardingService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void selfRegisterCreatesTrialGymRelationshipAndThenLogsIn() {
        OnboardingRegisterRequest request = registrationRequest();
        User user = User.builder().id(USER_ID).people(People.builder().email(request.email()).status(true).build()).build();
        when(authService.getAuthenticationManager()).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(new UserDetailsImpl(user), null));
        when(gymRepository.save(any(Gym.class))).thenAnswer(invocation -> invocation.getArgument(0, Gym.class).toBuilder().id(GYM_ID).build());
        when(usersGymRepository.save(any(UsersGym.class))).thenAnswer(invocation -> invocation.getArgument(0, UsersGym.class).toBuilder().id(USER_GYM_ID).build());
        when(roleRepository.findByName("Administrador")).thenReturn(Optional.empty());
        AuthGymResponse loginResponse = mock(AuthGymResponse.class);
        when(authService.loginWithGym(any())).thenReturn(loginResponse);

        AuthGymResponse response = onboardingService.selfRegister(request, null);

        ArgumentCaptor<Gym> savedGym = ArgumentCaptor.forClass(Gym.class);
        ArgumentCaptor<UsersGym> savedRelation = ArgumentCaptor.forClass(UsersGym.class);
        ArgumentCaptor<GymSubscription> trialSubscription = ArgumentCaptor.forClass(GymSubscription.class);
        verify(gymRepository).save(savedGym.capture());
        verify(usersGymRepository).save(savedRelation.capture());
        verify(gymSubscriptionRepository).save(trialSubscription.capture());
        assertThat(savedGym.getValue().getId()).isNull();
        assertThat(savedGym.getValue().getGymStatus()).hasToString("TRIAL");
        assertThat(savedGym.getValue().getTrialExpiresAt()).isAfter(java.time.LocalDateTime.now().plusDays(13));
        assertThat(savedRelation.getValue()).extracting(UsersGym::getUserId, UsersGym::getGymId, UsersGym::getStatus)
                .containsExactly(USER_ID, GYM_ID, true);
        assertThat(trialSubscription.getValue()).extracting(GymSubscription::getGymId, GymSubscription::getSaasPlanId, GymSubscription::getStartDate, GymSubscription::getEndDate)
                .containsExactly(GYM_ID, null, LocalDate.now(), LocalDate.now().plusDays(14));
        assertThat(response).isSameAs(loginResponse);
        verify(authService).register(any());
        verify(authService).loginWithGym(new com.conovax.fitflow.shared.application.dto.request.LoginWithGymRequest(request.email(), request.password(), USER_GYM_ID));
    }

    @Test
    void createOwnGymRejectsRequestWhenMunicipalityDoesNotExist() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailsImpl(User.builder().id(USER_ID).people(People.builder().status(true).build()).build()), null, List.of()));
        OnboardingGymRequest request = new OnboardingGymRequest("FitFlow", "900123", 55L, null, null);
        when(municipalityRepository.findByIdAndStatusTrue(request.municipalitieId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> onboardingService.createOwnGym(request, null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Municipio");

        verify(gymRepository, never()).save(any());
    }

    @Test
    void createOwnGymRequiresAnAuthenticatedUser() {
        SecurityContextHolder.clearContext();

        assertThatThrownBy(() -> onboardingService.createOwnGym(new OnboardingGymRequest("FitFlow", "900123", 55L, null, null), null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("sesión activa");

        verify(municipalityRepository, never()).findByIdAndStatusTrue(any());
    }

    private OnboardingRegisterRequest registrationRequest() {
        return new OnboardingRegisterRequest("Ana", "Pérez", "ana@example.test", "10203040", "secret", 1L, 2L, 3L, "FitFlow", "900123", 4L, "3001234567", "gym@example.test", null, null);
    }
}
