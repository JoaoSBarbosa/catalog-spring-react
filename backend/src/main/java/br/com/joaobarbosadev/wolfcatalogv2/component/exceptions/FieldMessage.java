package br.com.joaobarbosadev.wolfcatalogv2.component.exceptions;

import lombok.Data;

import java.io.Serializable;

@Data
public class FieldMessage implements Serializable {
    private String field;
    private String message;

    public FieldMessage() {

    }
    public FieldMessage(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
