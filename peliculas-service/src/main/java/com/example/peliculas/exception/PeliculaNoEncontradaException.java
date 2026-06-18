package com.example.peliculas.exception;

public class PeliculaNoEncontradaException extends RuntimeException {

    public PeliculaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
