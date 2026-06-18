package com.example.reservas.webclient;

// Importamos Map porque la respuesta del microservicio cliente
// será recibida como una estructura tipo clave-valor, similar a un JSON.
import java.util.Map;

// Importamos @Value para leer valores desde application.yml.
// En este caso, leeremos la URL del microservicio cliente.
import org.springframework.beans.factory.annotation.Value;

// Importamos @Component para indicar que esta clase será administrada por Spring.
// Así, Spring podrá crear un objeto de esta clase automáticamente.
import org.springframework.stereotype.Component;

// Importamos WebClient, que sirve para hacer peticiones HTTP
// desde un microservicio hacia otro microservicio.
import org.springframework.web.reactive.function.client.WebClient;

// Esta clase representa un "cliente HTTP" que se encargará
// de comunicarse con el microservicio de clientes.
@Component
public class ClienteClient {

    // Aquí declaramos una variable de tipo WebClient.
    // Este objeto será el encargado de enviar solicitudes HTTP al otro microservicio.
    private final WebClient webClient;

    // Este es el constructor de la clase.
    // Spring inyectará aquí la URL definida en application.yml,
    // por ejemplo: http://localhost:8081/api/v1/clientes
    public ClienteClient(@Value("${cliente-service.url}") String clienteServidor) {

        // Aquí construimos el WebClient y le indicamos cuál será
        // la URL base del microservicio cliente.
        // Gracias a eso, después solo agregaremos el "/{id}" en la consulta.
        this.webClient = WebClient.builder()
                .baseUrl(clienteServidor)
                .build();
    }

    // Este método se usa para consultar al microservicio cliente
    // y verificar si existe un cliente con el id recibido.
    //
    // Recibe:
    // id -> el identificador del cliente que queremos buscar
    //
    // Retorna:
    // un Map<String, Object>, que representa el JSON devuelto
    // por el microservicio cliente.
    public Map<String, Object> obtenerClienteId(Long id) {

        // Comenzamos una petición HTTP de tipo GET.
        return this.webClient.get()

                // Aquí agregamos el id a la URL.
                // Si la base era: http://localhost:8081/api/v1/clientes
                // y el id es 5, la URL final será:
                // http://localhost:8081/api/v1/clientes/5
                .uri("/{id}", id)

                // Enviamos la solicitud y comenzamos a recuperar la respuesta.
                .retrieve()


                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> {
                            System.out.println("Error detectado: " + response.statusCode());
                            return response.bodyToMono(String.class)
                                    .map(body -> new RuntimeException("Error en comunicación: " + response.statusCode()));
                        }
                )

                // Convertimos el cuerpo de la respuesta a un Map.
                // Esto nos permite leer el JSON como pares clave-valor.
                .bodyToMono(Map.class)

                // block() hace que el programa espere la respuesta
                // antes de seguir avanzando.
                //
                // Es decir, aunque WebClient puede trabajar de forma asíncrona,
                // aquí lo estamos usando de forma más simple y directa:
                // "pregunta y espera la respuesta".
                .block();
    }
}