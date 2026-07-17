package com.conovax.fitflow.infrastructure.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireFeature {
	/**
	 * Código de la feature SaaS requerida (e.g. "BIOMETRIC_ACCESS").
	 */
	String value();
}
