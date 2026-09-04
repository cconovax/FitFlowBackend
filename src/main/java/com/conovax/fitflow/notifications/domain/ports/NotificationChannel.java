package com.conovax.fitflow.notifications.domain.ports;

/**
 * Canales de notificación soportados por la plataforma.
 * Hoy solo {@link #EMAIL} tiene una implementación activa; SMS y WHATSAPP quedan
 * declarados para que {@code NotificationService} los pueda enrutar en el futuro
 * sin cambiar la API pública del módulo.
 */
public enum NotificationChannel {
	EMAIL,
	SMS,
	WHATSAPP
}
