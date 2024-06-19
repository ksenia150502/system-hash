package edu.java.gateway.controller;

import edu.java.gateway.entity.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

public interface ParameterApi {
    @Operation(summary = "Получить параметры")
    List<Parameter> getAllParameters();
}
