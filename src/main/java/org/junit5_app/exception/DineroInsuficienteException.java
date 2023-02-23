package org.junit5_app.exception;

public class DineroInsuficienteException extends RuntimeException{
    //para personalizar el error
    public DineroInsuficienteException(String message) {
        super(message);
    }
}
