package ar.edu.utn.frc.msclientessolicitudes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class LogDemoController {

    private static final Logger logger = LoggerFactory.getLogger(LogDemoController.class);

    // GET http://localhost:808X/logs/info
    @GetMapping("/info")
    public String info() {
        logger.info("Log INFO de prueba desde clientes-solicitudes");
        return "log info ok (clientes-solicitudes)";
    }

    // GET http://localhost:808X/logs/warn
    @GetMapping("/warn")
    public String warn() {
        logger.warn("Log WARN de prueba desde clientes-solicitudes");
        return "log warn ok (clientes-solicitudes)";
    }

    // GET http://localhost:808X/logs/error
    @GetMapping("/error")
    public String error() {
        logger.error("Log ERROR de prueba desde clientes-solicitudes");
        return "log error ok (clientes-solicitudes)";
    }
}
