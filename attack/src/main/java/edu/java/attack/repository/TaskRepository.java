package edu.java.attack.repository;

import edu.java.attack.entity.ProcessingStatus;
import edu.java.attack.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTaskByStatus(ProcessingStatus status);
}
