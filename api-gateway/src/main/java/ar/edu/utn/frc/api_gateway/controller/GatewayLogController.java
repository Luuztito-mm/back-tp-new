package ar.edu.utn.frc.api_gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway/logs")
public class GatewayLogController {

    private static final Logger logger = LoggerFactory.getLogger(GatewayLogController.class);

    // GET http://localhost:8080/gateway/logs/info
    @GetMapping("/info")
    public String info() {
        logger.info("Log INFO desde API Gateway");
        return "gateway log info ok";
    }

    // GET http://localhost:8080/gateway/logs/warn
    @GetMapping("/warn")
    public String warn() {
        logger.warn("Log WARN desde API Gateway");
        return "gateway log warn ok";
    }

    // GET http://localhost:8080/gateway/logs/error
    @GetMapping("/error")
    public String error() {
        logger.error("Log ERROR desde API Gateway");
        return "gateway log error ok";
    }
}
