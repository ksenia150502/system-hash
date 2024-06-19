package edu.java.gateway.service;

import edu.java.gateway.entity.Result;
import edu.java.gateway.entity.ResultDTO;
import edu.java.gateway.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
public class ResultService {
    private final ResultRepository resultRepository;

    @Autowired
    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public ResultDTO getResult(String id) {
        byte[] decodedBytes = Base64.getDecoder().decode(id);
        String decodedString = new String(decodedBytes);
        UUID uuid = UUID.fromString(decodedString);

        Result result = resultRepository.findById(uuid).orElseThrow();

        return new ResultDTO(result.getTask().getStatus(), result.getMessage());
    }
}
