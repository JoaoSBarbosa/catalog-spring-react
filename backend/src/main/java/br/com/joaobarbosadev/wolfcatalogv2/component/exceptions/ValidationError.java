package br.com.joaobarbosadev.wolfcatalogv2.component.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValidationError extends StandardError{

    private List<FieldMessage> fieldErrors = new ArrayList<>();

    public void addFieldError(String field, String message) {
        fieldErrors.add(new FieldMessage(field, message));
    }

}
