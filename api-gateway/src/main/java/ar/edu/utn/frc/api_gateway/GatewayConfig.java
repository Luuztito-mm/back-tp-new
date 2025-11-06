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
                // todo lo que entre a /camiones/** → va al microservicio en 8082
                .route("camiones", r -> r
                        .path("/camiones/**")
                        .uri("http://localhost:8082"))
                .build();
    }
}

