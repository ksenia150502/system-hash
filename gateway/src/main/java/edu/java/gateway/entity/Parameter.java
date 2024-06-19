package edu.java.gateway.entity;

import lombok.Getter;

@Getter
public enum Parameter {
    DIFF_ANALYSIS("Дифференциальный анализ");
    private final String title;

    Parameter(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "title='" + title + '\'' +
                '}';
    }
}
