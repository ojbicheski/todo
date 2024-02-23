/**
 * 
 */
package ca.bicheski.todo.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * 
 */
@SuppressWarnings("serial")
public abstract class TodoException extends RuntimeException {

	@Getter
	private String code;
	@Getter
	private DetailedMessage[] details;
	
	/**
	 * 
	 */
	public TodoException() {
		this.code = "none";
		this.details = new DetailedMessage[] {};
	}

	/**
	 * @param message
	 */
	public TodoException(String message) {
		super(message);
		this.code = "none";
		this.details = new DetailedMessage[] {};
	}

	/**
	 * @param cause
	 */
	public TodoException(Throwable cause) {
		super(cause);
		this.code = "none";
		this.details = new DetailedMessage[] {};
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TodoException(String message, Throwable cause) {
		super(message, cause);
		this.code = "none";
		this.details = new DetailedMessage[] {};
	}

	public TodoException(String code, String message) {
		super(message);
		this.code = code;
		this.details = new DetailedMessage[] {};
	}

	public TodoException(String code, String message, DetailedMessage...details) {
		super(message);
		this.code = code;
		this.details = details;
	}

	/**
	 * @return {@link HttpStatus}
	 */
	public abstract HttpStatus getStatus();
	
}
