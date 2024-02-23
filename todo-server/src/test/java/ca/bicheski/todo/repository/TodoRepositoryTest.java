/**
 * 
 */
package ca.bicheski.todo.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import ca.bicheski.todo.entity.Todo;

/**
 * 
 */
@DataJpaTest(showSql = true)
@ActiveProfiles(profiles = "test")
class TodoRepositoryTest {
	
	@Autowired
	private TodoRepository repository;

	@BeforeEach
	void setup() {
		repository.deleteAll();
	}
	
	@Test
	@DisplayName("Should return a empty List")
	void shouldReturnAEmptyList() {
		assertTrue(repository.findAll().isEmpty());
	}

	@Test
	@DisplayName("Should return one Todo")
	void shouldReturnOneTodo() {
		repository.save(Todo.builder().content("task").build());
		assertEquals(1, repository.findAll().size());
	}

	@Test
	@DisplayName("Should save a Todo with content 'task'")
	void shouldSaveATodoWithContentTask() {
		Todo todo = repository.save(Todo.builder().content("task").build());
		assertEquals("task", repository.findById(todo.getId()).map(Todo::getContent).orElse(""));
	}

	@Test
	@DisplayName("Should update a Todo to completed 'TRUE'")
	void shouldUpdateATodoToCompletedTRUE() {
		Todo todo = repository.save(Todo.builder().content("task").build());
		assertFalse(repository.findById(todo.getId()).map(Todo::isCompleted).orElse(true));
		todo.setCompleted(true);
		repository.save(todo);
		assertTrue(repository.findById(todo.getId()).map(Todo::isCompleted).orElse(false));
	}

	@Test
	@DisplayName("Should delete Todo")
	void shouldDelteTodo() {
		Todo todo = repository.save(Todo.builder().content("task").build());
		assertTrue(repository.findById(todo.getId()).isPresent());
		repository.deleteById(todo.getId());
		assertFalse(repository.findById(todo.getId()).isPresent());
	}
}
