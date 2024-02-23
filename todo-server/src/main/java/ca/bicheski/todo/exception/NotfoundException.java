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
public class NotfoundException extends TodoException {

	@Getter
	private HttpStatus status = HttpStatus.NOT_FOUND;

	public NotfoundException() {
		super();
	}

	public NotfoundException(String code, String message, DetailedMessage... details) {
		super(code, message, details);
	}

	public NotfoundException(String code, String message) {
		super(code, message);
	}

	public NotfoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotfoundException(String message) {
		super(message);
	}

	public NotfoundException(Throwable cause) {
		super(cause);
	}
	
}
