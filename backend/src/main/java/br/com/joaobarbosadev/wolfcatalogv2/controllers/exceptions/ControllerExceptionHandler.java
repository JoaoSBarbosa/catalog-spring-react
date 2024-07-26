package br.com.joaobarbosadev.wolfcatalogv2.controllers.exceptions;

import br.com.joaobarbosadev.wolfcatalogv2.component.exceptions.StandardError;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<StandardError> handleNotFound(ControllerNotFoundException ex, HttpServletRequest request) {
        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setMessage(ex.getMessage());
        error.setError("Entidade não localizada 😶‍🌫️");
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setPath(request.getRequestURI());
        return new ResponseEntity<>(error, status);
    }
}
