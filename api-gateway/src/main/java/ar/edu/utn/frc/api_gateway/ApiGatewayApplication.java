package ar.edu.utn.frc.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    // endpoint de prueba para saber si 8080 responde
    @RestController
    static class PingController {
        @GetMapping("/ping")
        public String ping() {
            return "gateway-ok";
        }
    }
}
