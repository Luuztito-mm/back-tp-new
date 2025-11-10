package ar.edu.utn.frc.msclientessolicitudes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsClientesSolicitudesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsClientesSolicitudesApplication.class, args);
    }
}
