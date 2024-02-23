/**
 * 
 */
package ca.bicheski.todo.exception;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 */
@Getter
@Setter
public class ErrorMessage {

	private ZonedDateTime timestamp;
	private String code;
	private String message;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<DetailedMessage> details;
	
	public ErrorMessage() {
		this.timestamp = ZonedDateTime.now();
		this.details = new ArrayList<>();
	}
	
	public static ErrorMessage of(TodoException e) {
		ErrorMessage error = new ErrorMessage();
		error.code = e.getCode();
		error.message = e.getMessage();
		if (e.getDetails().length > 0) {
			Collections.addAll(error.details, Arrays.stream(e.getDetails()).toArray(DetailedMessage[]::new));
		}
		
		return error;
	}
	
	public static ErrorMessage of(MethodArgumentNotValidException e) {
		ErrorMessage error = new ErrorMessage();
		error.message = e.getMessage();
		
		e.getAllErrors().stream()
			.map(err -> { 
				return new String[] { err.getCodes()[0], err.getDefaultMessage()}; 
			})
			.map(DetailedMessage::new)
			.forEach(detail -> error.details.add(detail));
		
		return error;
	}
}
