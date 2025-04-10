package com.aurionpro.lms.exception;

public class UserNotRegisteredException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserNotRegisteredException(String message) {
		super(message);
	}
}