/**
 * 
 */
package ca.bicheski.todo.dto;

import ca.bicheski.todo.entity.Todo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

	private Long id;
	@NotBlank
	@Size(min = 3, max = 500, message = "The task content must have between 3 and 500 characters.")
	private String content;
	private boolean completed;
	
	public Todo todo() {
		return Todo.builder()
				.id(id)
				.content(content)
				.completed(completed)
				.build();
	}
}
