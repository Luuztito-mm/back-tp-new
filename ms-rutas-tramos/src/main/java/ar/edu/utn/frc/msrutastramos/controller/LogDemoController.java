package ar.edu.utn.frc.msrutastramos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/logs")
public class LogDemoController {

    private static final Logger logger = LoggerFactory.getLogger(LogDemoController.class);

    @GetMapping("/info/{msg}")
    public ResponseEntity<String> logInfo(@PathVariable("msg") String msg) {
        System.out.println("Sout INFO (" + msg + ") [" + LocalDateTime.now() + "]");
        logger.info("Log INFO desde ms-rutas-tramos ({})", msg);
        return ResponseEntity.ok("log info ok (" + msg + ")");
    }

    @GetMapping("/warn/{msg}")
    public ResponseEntity<String> logWarn(@PathVariable("msg") String msg) {
        System.out.println("Sout WARN (" + msg + ") [" + LocalDateTime.now() + "]");
        logger.warn("Log WARN desde ms-rutas-tramos ({})", msg);
        return ResponseEntity.ok("log warn ok (" + msg + ")");
    }

    @GetMapping("/error/{msg}")
    public ResponseEntity<String> logError(@PathVariable("msg") String msg) {
        try {
            throw new RuntimeException("Error simulado: " + msg);
        } catch (Exception ex) {
            System.err.println("Sout ERROR (" + msg + ") [" + LocalDateTime.now() + "]");
            logger.error("Log ERROR desde ms-rutas-tramos ({})", msg, ex);
        }
        return ResponseEntity.internalServerError().body("log error ok (" + msg + ")");
    }
}
