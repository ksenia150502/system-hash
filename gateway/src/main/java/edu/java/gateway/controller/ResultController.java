package edu.java.gateway.controller;

import edu.java.gateway.entity.ResultDTO;
import edu.java.gateway.service.ResultService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/result")
public class ResultController implements ResultApi {
    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @Override
    @GetMapping("/{id}")
    public ResultDTO getResult(@PathVariable @Parameter(
            name = "id",
            description = "Идентификатор результата в формате Base64",
            example = "NDg5OTYzNGQtMmRkZC00MjExLTg3MGMtZWE2MmNkYjRhNzAw") String id) {
        return resultService.getResult(id);
    }
}
