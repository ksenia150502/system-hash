package edu.java.attack.config;

import edu.java.attack.entity.Task;
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

