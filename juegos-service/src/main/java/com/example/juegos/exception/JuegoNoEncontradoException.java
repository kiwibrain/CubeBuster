package com.example.juegos.exception;

public class JuegoNoEncontradoException extends RuntimeException {

    public JuegoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
