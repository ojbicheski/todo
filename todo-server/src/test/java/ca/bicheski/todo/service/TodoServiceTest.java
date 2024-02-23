package ca.bicheski.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import ca.bicheski.todo.entity.Todo;
import ca.bicheski.todo.exception.BadRequestException;
import ca.bicheski.todo.exception.NotfoundException;
import ca.bicheski.todo.repository.TodoRepository;

@DataJpaTest
@ActiveProfiles(profiles = "test")
class TodoServiceTest {

	@Autowired
	private TodoRepository repository;
	
	private TodoService service;
	
	@BeforeEach
	void setup() {
		this.service = new TodoService(repository);
		repository.deleteAll();
	}
	
	@Test
	@DisplayName("Should return List with one Todo")
	void shouldReturnListWithOneTodo() {
		repository.save(Todo.builder().content("task").build());
		assertEquals(1, service.findAll().size());
	}
	
	@Test
	@DisplayName("Should find one Todo")
	void shouldFindOneTodo() {
		Todo todo = repository.save(Todo.builder().content("task").build());
		assertNotNull(service.find(todo.getId()));
	}
	
	@Test
	@DisplayName("Should throw NotfoundException")
	void shouldThrowNotfoundException() {
		assertThatExceptionOfType(NotfoundException.class)
			.isThrownBy(() -> service.find(1000L));
	}
	
	@Test
	@DisplayName("Should create Todo")
	void shouldCreateTodo() {
		Todo todo = service.create(Todo.builder().content("task").build());
		assertNotNull(todo);
		assertThat(new Predicate<Todo>() {
			@Override
			public boolean test(Todo t) {
				return t.getId() > 0 && "task".equals(t.getContent());
			}
		});
	}
	
	@Test
	@DisplayName("Should throw BadRequestException")
	void shouldThrowBadRequestException() {
		service.create(Todo.builder().content("task").build());
		assertThatExceptionOfType(BadRequestException.class)
			.isThrownBy(() -> service.create(Todo.builder().content("task").build()))
			.withMessageContaining("The Todo content must be unique");
	}
	
	@Test
	@DisplayName("Should update Todo")
	void shouldUpdateTodo() {
		Todo created = service.create(Todo.builder().content("task").build());
		created.setCompleted(true);
		Todo updated = service.update(created.getId(), created);
		assertTrue(updated.isCompleted());
	}
	
	@ParameterizedTest
	@MethodSource("update") 
	@DisplayName("Should throw BadRequestException or NotfoundException")
	void shouldThrowBadRequestExceptionOrNotfoundException(Long id, Todo todo, Class<RuntimeException> ex) {
		assertThatExceptionOfType(ex).isThrownBy(() -> service.update(id, todo));
	}
	
	static Stream<Arguments> update() {
		return Stream.of(
	      Arguments.of(0l, Todo.builder().build(), NotfoundException.class)
	  );
	}
	
	@Test
	@DisplayName("Should delete Todo")
	void shouldDeleteTodo() {
		Todo todo = service.create(Todo.builder().content("task").build());
		assertNotNull(todo);
		service.delete(todo.getId());
		assertTrue(service.findAll().isEmpty());
	}
	
	@Test
	@DisplayName("Should delete throw NotfoundException")
	void shouldDeleteThrowNotfoundException() {
		assertThatExceptionOfType(NotfoundException.class)
			.isThrownBy(() -> service.delete(1000L));
	}
}
