package edu.java.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FileIsEmptyException extends RuntimeException {
    public FileIsEmptyException(String message) {
        super(message);
    }
}
