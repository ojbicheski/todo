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
public class BadRequestException extends TodoException {

	@Getter
	private HttpStatus status = HttpStatus.BAD_REQUEST;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String code, String message, DetailedMessage... details) {
		super(code, message, details);
	}

	public BadRequestException(String code, String message) {
		super(code, message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(Throwable cause) {
		super(cause);
	}

}
