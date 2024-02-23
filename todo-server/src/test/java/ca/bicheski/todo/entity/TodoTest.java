/**
 * 
 */
package ca.bicheski.todo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * 
 */
@ActiveProfiles(profiles = "test")
class TodoTest {
	
	@Test
	void createTest() {
		Todo todo = Todo.builder().content("Task").build();
		assertFalse(todo.isCompleted());
		assertEquals("Task",todo.getContent());
	}
	
	@Test
	void updateTest() {
		Todo todo = Todo.builder().content("Task").build();
		assertFalse(todo.isCompleted());
		todo.setCompleted(true);
		assertTrue(todo.isCompleted());
	}	

}
