package ar.edu.utn.frc.mstarifascostos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    /**
     * Bean RestTemplate que usaremos para llamar a servicios externos,
     * como OSRM u otros microservicios.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
