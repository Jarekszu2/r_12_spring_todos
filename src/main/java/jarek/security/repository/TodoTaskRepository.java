package jarek.security.repository;

import jarek.security.model.TodoTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {
}
