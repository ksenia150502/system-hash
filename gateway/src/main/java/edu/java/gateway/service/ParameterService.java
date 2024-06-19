package edu.java.gateway.service;

import edu.java.gateway.entity.Parameter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterService {
    public List<Parameter> getAllParameters() {
        return List.of(Parameter.DIFF_ANALYSIS);
    }
}
