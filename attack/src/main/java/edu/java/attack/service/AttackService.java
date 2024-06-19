package edu.java.attack.service;

import edu.java.attack.entity.HashData;
import edu.java.attack.entity.ProcessingStatus;
import edu.java.attack.entity.Result;
import edu.java.attack.entity.Task;
import edu.java.attack.repository.ResultRepository;
import edu.java.attack.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class AttackService {
    private final ConcurrentLinkedQueue<Task> queue;
    private final TaskRepository taskRepository;
    private final ResultRepository resultRepository;
    private final Analysis analysis;


    @Autowired
    public AttackService(ConcurrentLinkedQueue<Task> queue,
                         TaskRepository taskRepository,
                         ResultRepository resultRepository,
                         Analysis analysis) {
        this.queue = queue;
        this.taskRepository = taskRepository;
        this.resultRepository = resultRepository;
        this.analysis = analysis;
    }

    @Scheduled(fixedRate = 10000)
    public void attack() {
        Task task;
        if ((task = queue.poll()) != null) {
            List<HashData> hashDataList = task.getHashDataList();
            Result res = task.getResult();
            task.setStatus(ProcessingStatus.ATTACK);
            List<Double> resultAnalysis = analysis.attack(hashDataList);
            double sum = 0;
            double abs;
            for (double d : resultAnalysis) {
                sum += d;
            }
            if (resultAnalysis.isEmpty()) {
                return;
            } else {
                abs = sum / resultAnalysis.size();
            }
            res.setMessage("При изменении одного символа во входных данных в среднем количество совпавших данных в хэшах cоставляет " + abs * 100 + "%");
            task.setStatus(ProcessingStatus.ATTACK);
            taskRepository.save(task);
            resultRepository.save(res);
        }
    }
}
