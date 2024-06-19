package edu.java.attack.entity;

import lombok.Getter;

@Getter
public enum ProcessingStatus {
    NOT_PROCESSED("Не обработан"),
    EXECUTION_BY_INTERPRETER("Обрабатывается интерпретатором"),
    EXECUTED_BY_INTERPRETER("Обработан интерпретатором"),
    ATTACK("Обрабатывается атакой"),
    ATTACKED("Обработан атакой");

    private final String title;

    ProcessingStatus(String title) {
        this.title = title;
    }
}
