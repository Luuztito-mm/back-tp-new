package ar.edu.utn.frc.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                 // --- Camiones y Transportistas ---
                .route("camiones", r -> r
                        .path("/camiones/**")
                        .uri("http://host.docker.internal:8082"))

                // --- Clientes y Solicitudes ---
                .route("clientes-solicitudes", r -> r
                        .path(
                                "/solicitudes/**",
                                "/clientes/**",
                                "/contenedores/**",
                                "/eventos-seguimiento/**"
                        )
                        .uri("http://host.docker.internal:8081"))

                // --- Depósitos ---
                .route("depositos", r -> r
                        .path("/depositos/**")     // lo que ve "afuera" por el gateway
                        .uri("http://host.docker.internal:8083")) // micro ms-depositos en Docker
                // --- Tarifas y Costos ---
                .route("tarifas-costos", r -> r
                        .path("/tarifas/**")
                        .uri("http://host.docker.internal:8085"))

                // --- Rutas y Tramos ---
                .route("rutas-tramos", r -> r
                        .path(
                                "/rutas/**",
                                "/tramos/**"
                        )
                        .uri("http://host.docker.internal:8084")
                )


                // NO hace falta una ruta para /gateway/logs/** porque ya lo atiende el controller del gateway
                .build();
                

    }
}
