package ar.edu.utn.frc.mscamionestransportistas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class LogDemoController {

    private static final Logger logger = LoggerFactory.getLogger(LogDemoController.class);

    // GET http://localhost:8082/logs/info
    @GetMapping("/info")
    public String logInfo() {
        logger.info("Log INFO de prueba desde /logs/info");
        return "log info ok";
    }

    // GET http://localhost:8082/logs/warn
    @GetMapping("/warn")
    public String logWarn() {
        logger.warn("Log WARN de prueba desde /logs/warn");
        return "log warn ok";
    }

    // GET http://localhost:8082/logs/error
    @GetMapping("/error")
    public String logError() {
        logger.error("Log ERROR de prueba desde /logs/error");
        return "log error ok";
    }
}
