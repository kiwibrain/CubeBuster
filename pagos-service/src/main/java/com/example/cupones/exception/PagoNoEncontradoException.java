package com.example.cupones.exception;

public class PagoNoEncontradoException extends RuntimeException {

    public PagoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
