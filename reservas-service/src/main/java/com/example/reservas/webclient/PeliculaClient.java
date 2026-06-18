package com.example.reservas.webclient;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Component
public class PeliculaClient {

    private final WebClient webClient;

    public PeliculaClient(@Value("${pelicula-service.url}") String peliculaServidor) {

        this.webClient = WebClient.builder()
                .baseUrl(peliculaServidor)
                .build();
    }

    public Map<String, Object> obtenerPeliculaId(Long idPelicula) {

        // Comenzamos una petición HTTP de tipo GET.
        return this.webClient.get()
                .uri("/{idPelicula}", idPelicula)

                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Pelicula no encontrada"))
                )
                .bodyToMono(Map.class)
                .block();
    }
}