package ar.edu.utn.frc.api_gateway.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GatewayLogJobs {

    private static final Logger logger = LoggerFactory.getLogger(GatewayLogJobs.class);

    @Scheduled(fixedRate = 10000) // cada 10 segundos
    public void heartbeat() {
        logger.info("Gateway heartbeat [{}]", LocalDateTime.now());
    }
}
