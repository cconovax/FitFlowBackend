package com.conovax.fitflow.domain.exceptions;

public class SubscriptionInactiveException extends RuntimeException {
	public SubscriptionInactiveException(String message) {
		super(message);
	}
}