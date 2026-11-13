package com.conovax.fitflow.scheduling.domain.exceptions;

/** Regla de negocio de reservas incumplida por la petición (HTTP 400). */
public class SchedulingRuleViolationException extends IllegalArgumentException {
    public SchedulingRuleViolationException(String message) {
        super(message);
    }
}
