package org.example.demo.demoproject.apiexeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public ApiException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

}
