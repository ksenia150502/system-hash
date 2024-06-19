package edu.java.gateway.controller;

import edu.java.gateway.entity.Parameter;
import edu.java.gateway.service.ParameterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parameter")
public class ParameterController implements ParameterApi {
    private final ParameterService parameterService;

    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @GetMapping("")
    public List<Parameter> getAllParameters() {
        return parameterService.getAllParameters();
    }
}
