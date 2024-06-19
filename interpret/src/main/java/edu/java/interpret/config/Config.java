package edu.java.interpret.config;

import edu.java.interpret.entity.Task;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentLinkedQueue;

@Configuration
public class Config {
    @Bean
    public ConcurrentLinkedQueue<Task> getQueue() {
        return new ConcurrentLinkedQueue<>();
    }
}
