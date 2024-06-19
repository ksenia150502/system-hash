package edu.java.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultDTO {
    private ProcessingStatus status;
    private String message;
}
