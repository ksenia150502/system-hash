package edu.java.interpret.service;

import edu.java.interpret.entity.ProcessingStatus;
import edu.java.interpret.entity.Task;
import edu.java.interpret.repository.TaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class CheckingNewRequestsService {
    private final TaskRepository taskRepository;
    private final ConcurrentLinkedQueue<Task> queue;

    public CheckingNewRequestsService(TaskRepository taskRepository, ConcurrentLinkedQueue<Task> queue) {
        this.taskRepository = taskRepository;
        this.queue = queue;
    }

    @Scheduled(fixedRate = 10000)
    public void checkNewRequests() {
        List<Task> iterable = taskRepository.findTaskByStatus(ProcessingStatus.NOT_PROCESSED);
        queue.addAll(iterable);
    }
}
