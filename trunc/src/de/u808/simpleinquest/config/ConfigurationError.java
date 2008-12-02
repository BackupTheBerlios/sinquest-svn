package de.u808.simpleinquest.config;

public class ConfigurationError {
	
	public enum Severity {FATAL, ERROR, WARNING};
	
	private Severity severity;
	
	private String message;
	
	private Exception exception;

	public ConfigurationError(String message, Severity severity){
		this(message, severity, null);
	}
	
	public ConfigurationError(String message, Severity severity, Exception e){
		this.severity = severity;
		this.message = message;
		this.exception = e;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
	
}
