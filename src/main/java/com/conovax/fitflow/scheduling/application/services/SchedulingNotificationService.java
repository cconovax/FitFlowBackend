package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.gym.application.dto.response.UserGymContact;
import com.conovax.fitflow.gym.application.services.UserGymUsersService;
import com.conovax.fitflow.notifications.application.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/** Emails de reservas: efecto secundario no bloqueante (ADR 0003), enviado tras el commit y sin propagar fallos. */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulingNotificationService {
    static final String TRAINER_CHANGED_TEMPLATE = "class_trainer_changed";
    static final String WAITLIST_OFFER_TEMPLATE = "class_waitlist_offer";
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final NotificationService notificationService;
    private final UserGymUsersService userGymUsersService;

    @Value("${app.reset-password.base-url:http://localhost:5173}")
    private String frontendUrl;

    public void notifyTrainerChanged(Long gymId, String className, String previousTrainerName, String newTrainerName,
                                     Map<Long, List<LocalDateTime>> sessionsByUserGym) {
        if (sessionsByUserGym.isEmpty()) return;
        Map<Long, UserGymContact> contacts = resolveContacts(gymId, sessionsByUserGym.keySet());
        runAfterCommit(() -> {
            sessionsByUserGym.forEach((userGymId, sessions) -> {
                UserGymContact contact = contacts.get(userGymId);
                if (contact == null || contact.email() == null || contact.email().isBlank()) return;
                send(contact.email(), TRAINER_CHANGED_TEMPLATE, Map.of(
                        "userName", contact.fullName(),
                        "className", className,
                        "previousTrainerName", previousTrainerName,
                        "newTrainerName", newTrainerName,
                        "sessions", sessions.stream().map(DATE_TIME::format).toList()));
            });
        });
    }

    public void notifyWaitlistOffer(Long gymId, Long userGymId, String className, LocalDateTime startsAt,
                                    LocalDateTime expiresAt, int expirationMinutes) {
        UserGymContact contact = resolveContacts(gymId, List.of(userGymId)).get(userGymId);
        runAfterCommit(() -> {
            if (contact == null || contact.email() == null || contact.email().isBlank()) return;
            send(contact.email(), WAITLIST_OFFER_TEMPLATE, Map.of(
                    "userName", contact.fullName(),
                    "className", className,
                    "startsAt", DATE_TIME.format(startsAt),
                    "expiresAt", DATE_TIME.format(expiresAt),
                    "expirationMinutes", expirationMinutes,
                    "appUrl", frontendUrl));
        });
    }

    private Map<Long, UserGymContact> resolveContacts(Long gymId, java.util.Collection<Long> userGymIds) {
        try {
            return userGymUsersService.getContactsByUserGymIds(gymId, userGymIds);
        } catch (RuntimeException e) {
            log.warn("No se pudieron resolver los contactos para notificar: {}", e.getMessage());
            return Map.of();
        }
    }

    private void send(String to, String templateCode, Map<String, Object> variables) {
        try {
            notificationService.sendEmail(to, templateCode, variables);
        } catch (RuntimeException e) {
            log.warn("No se pudo enviar el email '{}': {}", templateCode, e.getMessage());
        }
    }

    private void runAfterCommit(Runnable action) {
        Runnable safe = () -> {
            try {
                action.run();
            } catch (RuntimeException e) {
                log.warn("Fallo preparando notificación de reservas: {}", e.getMessage());
            }
        };
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    safe.run();
                }
            });
        } else {
            safe.run();
        }
    }
}
