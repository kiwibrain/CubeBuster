package com.example.resenas.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class JuegoClient {

    private final WebClient webClient;

    public JuegoClient(WebClient.Builder webClientBuilder,
                       @Value("${services.juegos.url}") String juegoServidor) {

        this.webClient = webClientBuilder
                .baseUrl(juegoServidor)  // Ej: http://JUEGOS-SERVICE
                .build();
    }


    public Map<String, Object> obtenerJuegoId(Long idJuego) {
        try {
            return this.webClient.get()
                    .uri("/{id}", idJuego)  // URL final: http://JUEGOS-SERVICE/5
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                if (response.statusCode().value() == 404) {
                                    return Mono.error(new RuntimeException("Juego no encontrado con ID: " + idJuego));
                                }
                                return response.bodyToMono(String.class)
                                        .flatMap(body -> Mono.error(
                                                new RuntimeException("Error en microservicio de juegos: " + response.statusCode())
                                        ));
                            }
                    )
                    .bodyToMono(Map.class)
                    .block();

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error de comunicación con el servicio de juegos: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al consultar el juego: " + e.getMessage(), e);
        }
    }


    public boolean existeJuego(Long idJuego) {
        try {
            Map<String, Object> juego = obtenerJuegoId(idJuego);
            return juego != null && !juego.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}