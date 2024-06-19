package edu.java.interpret.service;

import edu.java.interpret.entity.HashData;
import edu.java.interpret.entity.ProcessingStatus;
import edu.java.interpret.entity.Task;
import edu.java.interpret.repository.HashDataRepository;
import edu.java.interpret.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@Service
public class PythonInterpreter {
    private final ConcurrentLinkedQueue<Task> queue;
    private final FileDownloadService fileDownloadService;
    private final TaskRepository taskRepository;
    private final HashDataRepository hashDataRepository;
    private static final int TIMEOUT = 5;

    @Autowired
    public PythonInterpreter(ConcurrentLinkedQueue<Task> queue,
                             FileDownloadService fileDownloadService,
                             TaskRepository taskRepository,
                             HashDataRepository hashDataRepository) {
        this.queue = queue;
        this.fileDownloadService = fileDownloadService;
        this.taskRepository = taskRepository;
        this.hashDataRepository = hashDataRepository;
    }

    public static String execute(String scriptFileName, String resultFileName, String data) {
        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptFileName);
        StringBuilder result = new StringBuilder();

        try {
            Process process = processBuilder.start();
            Writer w = new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8);
            w.write(data);
            w.flush();
            w.close();
            if (!process.waitFor(TIMEOUT, TimeUnit.SECONDS)) {
                process.destroy();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new FileWriter(resultFileName));

            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
                bw.write(line);
            }
            br.close();
            bw.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return result.toString();
    }

    @Transactional
    @Scheduled(fixedRate = 10000)
    public void run() {
        Task task;
        if ((task = queue.poll()) != null) {
            UUID id = task.getResult().getId();
            String scriptFileName = id + ".py";
            String uri = task.getFileMetadata()
                    .getUrl().replace("https://upload.files.storage.yandexcloud.net/", "");

            byte[] bytes = fileDownloadService.downloadFile(uri);

            try (FileOutputStream fos = new FileOutputStream(scriptFileName)) {
                fos.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

            task.setStatus(ProcessingStatus.EXECUTION_BY_INTERPRETER);
            taskRepository.saveAndFlush(task);

            List<HashData> hashDataList = task.getHashDataList();
            for (HashData hashData : hashDataList) {
                String resultFileName = id + "_" + hashData.getId() + ".txt";
                String result = execute(scriptFileName, resultFileName, hashData.getData());
                try {
                    File resultFile = new File(resultFileName);
                    resultFile.delete();
                } catch (SecurityException e) {
                    throw new RuntimeException();
                }
                hashData.setHash(result);
            }

            try {
                File scriptFile = new File(scriptFileName);
                scriptFile.delete();
            } catch (SecurityException e) {
                throw new RuntimeException();
            }
            hashDataRepository.saveAll(hashDataList);
            task.setStatus(ProcessingStatus.EXECUTED_BY_INTERPRETER);
            taskRepository.save(task);
        }
    }
}
