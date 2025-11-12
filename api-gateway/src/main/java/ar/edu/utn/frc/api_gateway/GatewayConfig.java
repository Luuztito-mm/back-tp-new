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
                        // si querés que el micro reciba sin /camiones, descomentá esto:
                        .filters(f -> f.rewritePath("/camiones/(?<remaining>.*)", "/${remaining}"))
                        .uri("http://localhost:8082"))

                // --- Clientes y Solicitudes ---
                .route("clientes-solicitudes", r -> r
                        .path(
                                "/solicitudes/**",
                                "/clientes/**",
                                "/eventos-seguimiento/**"
                        )
                        // acá sí hay que sacar el prefijo correspondiente
                        .filters(f -> f
                                .rewritePath("/solicitudes/(?<remaining>.*)", "/${remaining}")
                                .rewritePath("/clientes/(?<remaining>.*)", "/${remaining}")
                                .rewritePath("/eventos-seguimiento/(?<remaining>.*)", "/${remaining}")
                        )
                        .uri("http://localhost:8081"))

                // --- Depósitos ---
                .route("depositos", r -> r
                        .path("/api/depositos/**")
                        .filters(f -> f.rewritePath("/api/depositos/(?<remaining>.*)", "/api/depositos/${remaining}"))
                        .uri("http://localhost:8083"))

                // --- Tarifas y costos ---
                .route("tarifas", r -> r
                        .path("/api/tarifas/**")

                
              // si querés también podés reescribir igual que arriba
                        .uri("http://localhost:8085"))

                // NO hace falta una ruta para /gateway/logs/** porque ya lo atiende el controller del gateway
                .build();

    }
}
