package edu.java.attack.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String message;

    @OneToOne(mappedBy = "result")
    @JsonBackReference
    private Task task;

    public Result() {
    }
}

