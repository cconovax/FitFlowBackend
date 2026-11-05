package com.conovax.fitflow.scheduling.domain.exceptions;

import com.conovax.fitflow.shared.domain.exceptions.DuplicateResourceException;

/** El estado actual de la reserva/sesión impide la operación (HTTP 409 vía GlobalExceptionHandler). */
public class SchedulingConflictException extends DuplicateResourceException {
    public SchedulingConflictException(String message) {
        super(message);
    }
}
