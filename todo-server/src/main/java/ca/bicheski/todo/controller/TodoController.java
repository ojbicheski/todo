/**
 * 
 */
package ca.bicheski.todo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import ca.bicheski.todo.dto.TodoDto;
import ca.bicheski.todo.entity.Todo;
import ca.bicheski.todo.exception.ErrorMessage;
import ca.bicheski.todo.exception.TodoException;
import ca.bicheski.todo.service.TodoService;
import jakarta.validation.Valid;

/**
 * 
 */
@CrossOrigin(
		maxAge = 3600
)
@RestController
@RequestMapping("/api/todos")
public class TodoController {
	
	private TodoService service;

	public TodoController(TodoService service) {
		super();
		this.service = service;
	}

	@GetMapping
	ResponseEntity<List<TodoDto>> findAll() {
		return ResponseEntity.ok(service.findAll().stream().map(Todo::dto).toList());
	}

	@GetMapping("/{id}")
	ResponseEntity<TodoDto> find(@PathVariable(required = true, name = "id") Long id) {
		return ResponseEntity.ok(service.find(id).dto());
	}
	
	@PostMapping
	ResponseEntity<TodoDto> create(@Valid @RequestBody TodoDto dto) {
		return ResponseEntity
				.created(URI.create("/todos"))
				.body(service.create(dto.todo()).dto());
	}
	
	@PutMapping("/{id}")
	ResponseEntity<TodoDto> update(
			@PathVariable(required = true, name = "id") Long id, 
			@Valid @RequestBody TodoDto dto) {
		return ResponseEntity
				.accepted()
				.body(service.update(id, dto.todo()).dto());
	}

	@DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void delete(@PathVariable(required = true, name = "id") Long id) {
      service.delete(id);
  }
	
	@ExceptionHandler({ TodoException.class })
	public ResponseEntity<?> handleNotfoundException(TodoException e, WebRequest request) {
		return ResponseEntity.status(e.getStatus()).body(ErrorMessage.of(e));
	}
	
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, 
			WebRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.of(e));
	}
}
