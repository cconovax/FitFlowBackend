package com.conovax.fitflow.shared.domain.exceptions;

public class SubscriptionInactiveException extends RuntimeException {
	public SubscriptionInactiveException(String message) {
		super(message);
	}
}