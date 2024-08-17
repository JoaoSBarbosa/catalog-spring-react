package br.com.joaobarbosadev.wolfcatalogv2.services.exceptions;

public class ControllerNotFoundException extends RuntimeException{

    public ControllerNotFoundException(String message){
        super(message);
    }
}
