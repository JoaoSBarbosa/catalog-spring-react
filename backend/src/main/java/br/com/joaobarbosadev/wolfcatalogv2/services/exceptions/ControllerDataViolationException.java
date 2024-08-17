package br.com.joaobarbosadev.wolfcatalogv2.services.exceptions;

public class ControllerDataViolationException extends RuntimeException{

    public ControllerDataViolationException(String message){
        super(message);
    }
}
