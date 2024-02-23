/**
 * 
 */
package ca.bicheski.todo.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedMessage {
	
	private String id;
	private String field;
	private String description;
	
	public DetailedMessage(String[] detail) {
		if(detail.length > 1) {
			id = detail[0];
			String[] codes = detail[0].replace(".",",").split("\\s*,\\s*");
			field = codes[codes.length-1];
			description = detail[1];
		} else {
			description = detail[0];
		}
	}
}
