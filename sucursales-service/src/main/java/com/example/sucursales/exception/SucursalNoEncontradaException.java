package com.example.sucursales.exception;

public class SucursalNoEncontradaException extends RuntimeException {
    public SucursalNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}