package ar.edu.utn.frc.msrutastramos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    /**
     * Bean de RestTemplate para que los services puedan
     * hacer llamadas HTTP a otros microservicios.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
