/**
 * 
 */
package ca.bicheski.todo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import ca.bicheski.todo.dto.TodoDto;
import ca.bicheski.todo.entity.Todo;
import ca.bicheski.todo.exception.ErrorMessage;
import ca.bicheski.todo.repository.TodoRepository;

/**
 * 
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
class TodoControllerTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate rest;

	@Autowired
	private TodoController controller;
	
	@Autowired
	private TodoRepository repository;
	
	private String urlBase;
	
	@BeforeEach
	void setup() {
		this.urlBase = "http://localhost:" + port + "/api/todos";
		repository.deleteAll();
	}
	
	@Test
	@DisplayName("Check the context loads")
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	@Test
	@DisplayName("Should not return Todos")
	void shouldNotReturnTodos() {
		ResponseEntity<List<TodoDto>> response = rest.exchange(
				urlBase, HttpMethod.GET, null, new ParameterizedTypeReference<List<TodoDto>>() { });
		
		assertNotNull(response);
		assertThat(response).matches(new Predicate<ResponseEntity<List<TodoDto>>>() {
			@Override
			public boolean test(ResponseEntity<List<TodoDto>> t) {
				return t.getBody().isEmpty() && t.getStatusCode().is2xxSuccessful();
			}
		});
	}
	
	@Test
	@DisplayName("Should return Todos")
	void shouldReturnTodos() {
		repository.save(Todo.builder().content("task").build());

		ResponseEntity<List<TodoDto>> response = rest.exchange(
				urlBase, HttpMethod.GET, null, new ParameterizedTypeReference<List<TodoDto>>() { });
		
		assertNotNull(response);
		assertThat(response).matches(new Predicate<ResponseEntity<List<TodoDto>>>() {
			@Override
			public boolean test(ResponseEntity<List<TodoDto>> t) {
				return !t.getBody().isEmpty() && t.getStatusCode().is2xxSuccessful();
			}
		});
		assertThat(response).matches(new Predicate<ResponseEntity<List<TodoDto>>>() {
			@Override
			public boolean test(ResponseEntity<List<TodoDto>> t) {
				return t.getBody().stream().anyMatch(todo -> "task".equalsIgnoreCase(todo.getContent()));
			}
		});
	}
	
	@Test
	@DisplayName("Should return Status Code 404")
	void shouldReturnStatusCode404() {
		ResponseEntity<ErrorMessage> response = rest.getForEntity(urlBase.concat("/900"), ErrorMessage.class);
		
		assertNotNull(response);
		assertThat(response).matches(new Predicate<ResponseEntity<ErrorMessage>>() {
			@Override
			public boolean test(ResponseEntity<ErrorMessage> t) {
				return t.getStatusCode().is4xxClientError();
			}
		});
	}
	
	@Test
	@DisplayName("Should create new Todo")
	void shouldCreateNewTodo() {
		ResponseEntity<TodoDto> response = rest.postForEntity(
				urlBase, Todo.builder().content("task").build(), TodoDto.class);
		
		assertNotNull(response);
		assertThat(response).matches(new Predicate<ResponseEntity<TodoDto>>() {
			@Override
			public boolean test(ResponseEntity<TodoDto> t) {
				return Objects.nonNull(t.getBody()) 
						&& t.getStatusCode().is2xxSuccessful()
						&& "task".equalsIgnoreCase(t.getBody().getContent())
						&& !t.getBody().getId().equals(0l);
			}
		});
	}
	
	@Test
	@DisplayName("Should not create new Todo")
	void shouldNotCreateNewTodo() {
		ResponseEntity<ErrorMessage> response = rest.postForEntity(
				urlBase, Todo.builder().content("ta").build(), ErrorMessage.class);
		
		assertNotNull(response);
		assertThat(response).matches(new Predicate<ResponseEntity<ErrorMessage>>() {
			@Override
			public boolean test(ResponseEntity<ErrorMessage> t) {
				return Objects.nonNull(t.getBody()) 
						&& t.getStatusCode().isError();
			}
		});
	}
	
	@Test
	@DisplayName("Should update Todo")
	void shouldUpdateTodo() {
		Todo todo = repository.save(Todo.builder().content("task").build());

		todo.setCompleted(true);
		HttpEntity<TodoDto> entity = new HttpEntity<>(todo.dto(), null);
		ResponseEntity<TodoDto> response = rest.exchange(
				urlBase.concat("/") + todo.getId(), HttpMethod.PUT, entity, TodoDto.class);
		
		assertNotNull(response);
		assertThat(response).matches(new Predicate<ResponseEntity<TodoDto>>() {
			@Override
			public boolean test(ResponseEntity<TodoDto> t) {
				return Objects.nonNull(t.getBody()) 
						&& t.getStatusCode().is2xxSuccessful();
			}
		});
	}
	
	@Test
	@DisplayName("Should throw BadRequestException - body is null")
	void shouldThrowBadRequestException() {
		HttpEntity<TodoDto> entity = new HttpEntity<>(TodoDto.builder().build(), null);
		ResponseEntity<ErrorMessage> response = rest.exchange(
				urlBase.concat("/100"), HttpMethod.PUT, entity, ErrorMessage.class);
		
		assertNotNull(response);
		assertThat(response).matches(new Predicate<ResponseEntity<ErrorMessage>>() {
			@Override
			public boolean test(ResponseEntity<ErrorMessage> t) {
				return t.getStatusCode().is4xxClientError();
			}
		});
	}
	
	@Test
	@DisplayName("Should delete Todo")
	void shouldDeleteTodo() {
		Todo todo = repository.save(Todo.builder().content("task").build());

		ResponseEntity<Void> response = rest.exchange(
				urlBase.concat("/") + todo.getId(), HttpMethod.DELETE, null, Void.class);
		
		assertNotNull(response);
		assertThat(response).matches(new Predicate<ResponseEntity<Void>>() {
			@Override
			public boolean test(ResponseEntity<Void> t) {
				return t.getStatusCode().is2xxSuccessful();
			}
		});
	}
}
