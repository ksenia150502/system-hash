package edu.java.attack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AttackApplication {
    public static void main(String[] args) {
        SpringApplication.run(AttackApplication.class, args);
    }
}
