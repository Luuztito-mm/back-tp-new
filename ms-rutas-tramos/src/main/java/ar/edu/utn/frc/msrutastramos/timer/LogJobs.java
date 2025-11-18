package ar.edu.utn.frc.msrutastramos.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class LogJobs {

    private static final Logger logger = LoggerFactory.getLogger(LogJobs.class);
    private final Random random = new Random();

    @Scheduled(fixedRate = 5000)
    public void tick() {
        if (random.nextBoolean()) {
            logger.info("Tick OK [RutasTramos - {}]", LocalDateTime.now());
        } else {
            logger.error("Tick ERROR [RutasTramos - {}]", LocalDateTime.now());
        }
    }
}
