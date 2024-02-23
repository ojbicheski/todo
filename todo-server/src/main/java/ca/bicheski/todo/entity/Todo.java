/**
 * 
 */
package ca.bicheski.todo.entity;

import ca.bicheski.todo.dto.TodoDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Entity
@Table(schema = "todo_schema",  name = "tb_todo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String content;
	@Default
	private boolean completed = false;
	
	public TodoDto dto() {
		return TodoDto.builder()
				.id(id)
				.content(content)
				.completed(completed)
				.build();
	}
}
