package com.conovax.fitflow.domain.ports;

/**
 * Objeto de valor que representa un email listo para enviar.
 */
public record EmailMessage(String to, String subject, String htmlBody) {}
