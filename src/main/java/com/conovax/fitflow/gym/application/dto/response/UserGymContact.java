package com.conovax.fitflow.gym.application.dto.response;

/** Datos mínimos de contacto de un user_gym para notificaciones y listados internos (sin documento ni teléfono). */
public record UserGymContact(Long userGymId, String fullName, String email) {
}
