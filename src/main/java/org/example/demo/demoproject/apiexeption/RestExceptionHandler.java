package org.example.demo.demoproject.apiexeption;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<String> handleApiError(ApiException e) throws IOException {
        log.error(e.getMessage());

        return ResponseEntity.status(e.getStatus()).contentType(MediaType.APPLICATION_JSON)
                .body(convertErrorToJSON(e));
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    protected ResponseEntity<String> handleApiError(AuthenticationCredentialsNotFoundException e) throws IOException {
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
                .body(convertErrorToJSON(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<String> handleApiError(MethodArgumentNotValidException e) throws IOException {
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(convertErrorToJSON(e));
    }

    private String convertErrorToJSON(Exception e) throws IOException {
        Map<String, String> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("message", e.getMessage());

        return objectMapper.writeValueAsString(error);
    }

}
