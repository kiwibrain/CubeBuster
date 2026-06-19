package com.example.cupones.exception;

public class CuponNoEncontradoException extends RuntimeException {
    public CuponNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}