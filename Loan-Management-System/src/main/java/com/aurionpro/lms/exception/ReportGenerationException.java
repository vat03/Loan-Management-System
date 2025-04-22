package com.aurionpro.lms.exception;

public class ReportGenerationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ReportGenerationException(String message) {
		super(message);
	}
	
	public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
