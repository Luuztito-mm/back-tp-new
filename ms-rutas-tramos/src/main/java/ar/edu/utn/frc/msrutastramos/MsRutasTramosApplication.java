package ar.edu.utn.frc.msrutastramos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Aplicación principal del microservicio ms-rutas-tramos.
 *
 * @EnableScheduling habilita la ejecución de tareas programadas
 * (los jobs definidos en la clase LogJobs).
 */
@SpringBootApplication
@EnableScheduling
public class MsRutasTramosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsRutasTramosApplication.class, args);
    }
}
