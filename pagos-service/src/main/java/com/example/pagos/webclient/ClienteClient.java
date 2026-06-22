package com.example.pagos.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClienteClient {

    private final WebClient webClient;

    public ClienteClient(
            WebClient.Builder webClientBuilder,
            @Value("${services.cliente.url}") String clienteServidor) {

        this.webClient = webClientBuilder
                .baseUrl(clienteServidor)
                .build();
    }

    public Map<String, Object> obtenerClienteId(Long id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> {
                            System.out.println("Error detectado: " + response.statusCode());
                            return response.bodyToMono(String.class)
                                    .map(body -> new RuntimeException("Error en comunicación: " + response.statusCode()));
                        }
                )
                .bodyToMono(Map.class)
                .block();
    }
}