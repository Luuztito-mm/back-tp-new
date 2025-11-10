package ar.edu.utn.frc.msclientessolicitudes.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogJobs {

    private static final Logger logger = LoggerFactory.getLogger(LogJobs.class);

    // Cada 5 segundos genera un log aleatorio (INFO o ERROR)
    @Scheduled(fixedRate = 5000)
    public void tick() {
        LocalDateTime now = LocalDateTime.now();

        try {
            // Simular error de forma aleatoria
            if (Math.random() < 0.3) {
                throw new RuntimeException("Tick falló a las " + now);
            }
            logger.info("Tick OK [ClientesSolicitudes - {}]", now);
        } catch (Exception e) {
            logger.error("Tick ERROR", e);
        }
    }
}
