package edu.java.gateway.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(FileNotUploadException.class)
    public ResponseEntity<ApiError> handleException(FileNotUploadException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.INSUFFICIENT_STORAGE.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INSUFFICIENT_STORAGE);
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ApiError> handleException(InvalidFileException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiError> handleException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.REQUEST_ENTITY_TOO_LARGE.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.REQUEST_ENTITY_TOO_LARGE);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleException(NoSuchElementException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
