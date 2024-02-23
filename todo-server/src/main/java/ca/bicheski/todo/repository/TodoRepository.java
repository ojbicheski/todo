/**
 * 
 */
package ca.bicheski.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bicheski.todo.entity.Todo;

/**
 * 
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
