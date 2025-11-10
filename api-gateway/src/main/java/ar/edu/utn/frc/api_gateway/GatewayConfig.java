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
                        .uri("http://localhost:8082"))
                // --- Clientes y Solicitudes ---
                .route("clientes-solicitudes", r -> r
                    .path( "/solicitudes/**") // ambos endpoints si los tenés
                    .filters(f -> f.rewritePath("/(?<remaining>.*)", "/${remaining}"))
                    .uri("http://localhost:8081")) // puerto del ms-clientes-solicitudes
              
                // --- Depósitos ---
                .route("depositos", r -> r
                        .path("/api/depositos/**")
                        .filters(f -> f.rewritePath("/api/depositos/(?<remaining>.*)", "/${remaining}"))
                        .uri("http://localhost:8083"))

                // --- Tarifas y costos ---
                .route("tarifas", r -> r
                        .path("/api/tarifas/**")
                        //.filters(f -> f.rewritePath("/api/tarifas/(?<remaining>.*)", "/${remaining}"))
                        .uri("http://localhost:8085"))

                .build();
                

    }
}

