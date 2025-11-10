package ar.edu.utn.frc.mscamionestransportistas.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@EnableScheduling
public class LogJobs {

    private static final Logger logger = LoggerFactory.getLogger(LogJobs.class);
    private static final long SEGUNDO = 1000;

    // arranca a los 15s y luego cada 5s
    @Scheduled(initialDelay = 15 * SEGUNDO, fixedDelay = 5 * SEGUNDO)
    public void tick() {
        Random rnd = new Random();
        if (rnd.nextInt(10) < 6) {
            System.out.println("Tick [" + LocalDateTime.now() + "]");
            logger.info("Tick OK [{}]", LocalDateTime.now());
        } else {
            try {
                throw new RuntimeException("Tick falló a las " + LocalDateTime.now());
            } catch (Exception ex) {
                System.err.println("Tick ERROR [" + LocalDateTime.now() + "]");
                logger.error("Tick ERROR", ex);
            }
        }
    }
}
