package br.com.joaobarbosadev.wolfcatalogv2.controllers.exceptions;

import br.com.joaobarbosadev.wolfcatalogv2.component.exceptions.StandardError;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerDataViolationException;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerNotFoundException;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerNullValuesException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Objects;

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

    @ExceptionHandler(ControllerNullValuesException.class)
    public ResponseEntity<StandardError> nullValues(ControllerNullValuesException ex, HttpServletRequest request) {
        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        error.setMessage(ex.getMessage());
        error.setError("Ausencia de valores obrigatórios 😶‍🌫️");
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setPath(request.getRequestURI());
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(ControllerDataViolationException.class)
    public ResponseEntity<StandardError> dataViolation(ControllerDataViolationException ex, HttpServletRequest request) {
        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        error.setMessage(ex.getMessage());
        error.setError("Violação de integridade do banco");
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setPath(request.getRequestURI());
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validations(MethodArgumentNotValidException ex, HttpServletRequest request) {
        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        error.setMessage("Erro ao validar os dados do banco");
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setPath(request.getRequestURI());
        error.setError(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(error, status);
    }
}
