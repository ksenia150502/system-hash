package edu.java.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INSUFFICIENT_STORAGE)
public class FileNotUploadException extends RuntimeException {
    public FileNotUploadException(String message) {
        super(message);
    }
}
