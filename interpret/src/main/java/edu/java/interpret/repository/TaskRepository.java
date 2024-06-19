package edu.java.interpret.repository;

import edu.java.interpret.entity.ProcessingStatus;
import edu.java.interpret.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTaskByStatus(ProcessingStatus status);
}
