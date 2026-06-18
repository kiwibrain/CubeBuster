package duoc.clientes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para automatizar la documentación de Swagger/OpenAPI 3
 * para el microservicio de Clientes en el ecosistema CubeBuster.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CubeBuster - Servicio de Clientes API")
                        .version("1.0.0")
                        .description("Microservicio encargado de la gestión integral de clientes, " +
                                "perfiles de usuario, membresías y autenticación base dentro de la plataforma.")
                        .contact(new Contact()
                                .name("Soporte de Desarrollo - Duoc UC")
                                .email("soporte@cubebuster.cl")));
    }
}