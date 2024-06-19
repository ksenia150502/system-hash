package edu.java.interpret;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InterpretApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterpretApplication.class, args);
    }

}

