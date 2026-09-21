package co.istad.tostripv1.exception;

import co.istad.tostripv1.base.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class ApiException {
    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
        ErrorResponse<?> errorResponse = ErrorResponse.builder()
                .code(e.getStatusCode().value())
                .reason(e.getReason())
                .build();
        return ResponseEntity
                .status(e.getStatusCode())
                .body(Map.of("error", errorResponse));
    }
    // Catch RuntimeException
    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        ErrorResponse<?> errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value()) // you can choose a status
                .reason(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", errorResponse));
    }
}