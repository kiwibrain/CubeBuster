package com.example.reservas.exception;

public class ReservaNoEncontradaException extends RuntimeException {

    public ReservaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
