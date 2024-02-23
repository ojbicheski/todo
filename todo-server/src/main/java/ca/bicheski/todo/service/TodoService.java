/**
 * 
 */
package ca.bicheski.todo.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ca.bicheski.todo.entity.Todo;
import ca.bicheski.todo.exception.BadRequestException;
import ca.bicheski.todo.exception.NotfoundException;
import ca.bicheski.todo.repository.TodoRepository;

/**
 * 
 */
@Service
public class TodoService {
	
	private TodoRepository repository;
	
	public TodoService(TodoRepository repository) {
		this.repository = repository;
	}

	public List<Todo> findAll() {
		return this.repository.findAll();
	}

	public Todo find(Long id) {
		return this.repository.findById(id)
				.orElseThrow(() -> new NotfoundException("todo.not_found", "Todo not available"));
	}
	
	public Todo create(Todo todo) {
		try {
			return repository.save(todo);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("The Todo content must be unique", e);
		}
	}
	
	public Todo update(Long id, Todo toUpdate) {
		Todo todo = find(id);
		todo.setCompleted(toUpdate.isCompleted());
		return repository.save(todo);
	}

	public void delete(Long id) {
		repository.delete(find(id));
	}
}
