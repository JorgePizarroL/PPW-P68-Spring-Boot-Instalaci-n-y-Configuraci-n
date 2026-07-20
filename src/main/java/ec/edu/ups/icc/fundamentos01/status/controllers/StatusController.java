package ec.edu.ups.icc.fundamentos01.status.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/*
 * Controlador público para verificar que el servicio está activo.
 *
 * No requiere autenticación (configurado en SecurityConfig como
 * /status/** -> permitAll).
 *
 * Ruta final: GET /api/status
 */
@RestController
@RequestMapping("/status")
public class StatusController {

    @GetMapping
    public Map<String, Object> status() {
        return Map.of(
                "service", "fundamentos01 API",
                "status", "running",
                "timestamp", LocalDateTime.now().toString()
        );
    }
}
