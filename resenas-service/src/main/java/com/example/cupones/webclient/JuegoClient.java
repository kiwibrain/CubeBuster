package com.example.cupones.webclient;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Component
public class JuegoClient {

    private final WebClient webClient;

    public JuegoClient(@Value("${juego-service.url}") String juegoServidor) {

        this.webClient = WebClient.builder()
                .baseUrl(juegoServidor)
                .build();
    }

    public Map<String, Object> obtenerJuegoId(Long idJuego) {

        // Comenzamos una petición HTTP de tipo GET.
        return this.webClient.get()
                .uri("/{idJuego}", idJuego)

                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Juego no encontrado"))
                )
                .bodyToMono(Map.class)
                .block();
    }
}