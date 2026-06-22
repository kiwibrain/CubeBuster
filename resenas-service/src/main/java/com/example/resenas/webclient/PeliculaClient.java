package com.example.resenas.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class PeliculaClient {

    private final WebClient webClient;

    public PeliculaClient(WebClient.Builder webClientBuilder,
                          @Value("${services.peliculas.url}") String peliculaServidor) {

        this.webClient = webClientBuilder
                .baseUrl(peliculaServidor)  // Ej: http://PELICULAS-SERVICE
                .build();
    }

    public Map<String, Object> obtenerPeliculaId(Long idPelicula) {
        try {
            return this.webClient.get()
                    .uri("/{id}", idPelicula)  // URL final: http://PELICULAS-SERVICE/5
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                if (response.statusCode().value() == 404) {
                                    return Mono.error(new RuntimeException("Película no encontrada con ID: " + idPelicula));
                                }
                                return response.bodyToMono(String.class)
                                        .flatMap(body -> Mono.error(
                                                new RuntimeException("Error en microservicio de películas: " + response.statusCode())
                                        ));
                            }
                    )
                    .bodyToMono(Map.class)
                    .block();

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error de comunicación con el servicio de películas: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al consultar la película: " + e.getMessage(), e);
        }
    }


    public boolean existePelicula(Long idPelicula) {
        try {
            Map<String, Object> pelicula = obtenerPeliculaId(idPelicula);
            return pelicula != null && !pelicula.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}