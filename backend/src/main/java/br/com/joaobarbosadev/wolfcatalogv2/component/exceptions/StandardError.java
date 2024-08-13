package br.com.joaobarbosadev.wolfcatalogv2.component.exceptions;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class StandardError implements Serializable {

    private Instant timestamp;
    private String error;
    private String message;
    private Integer status;
    private String path;

    public StandardError(Instant timestamp, String error, String message, Integer status, String path) {
        this.timestamp = timestamp;
        this.error = error;
        this.message = message;
        this.status = status;
        this.path = path;

    }
    public StandardError(){}


}
