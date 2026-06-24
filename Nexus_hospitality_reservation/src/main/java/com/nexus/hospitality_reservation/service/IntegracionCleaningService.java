package com.nexus.hospitality_reservation.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class IntegracionCleaningService {

    private final RestTemplate restTemplate = new RestTemplate();

    
    private static final String CLEANING_BASE_URL = "http://localhost:8083/limpiezas";

    public ResponseEntity<Map> crearTareaLimpieza(String codigoReserva, String habitacionId) {
        // MVP: LimpiezaRequest tiene referencia (String), habitacionId (Long)
        Long habitacionLong;
        try {
            habitacionLong = Long.valueOf(habitacionId);
        } catch (Exception e) {
            
            throw new IllegalArgumentException("habitacionId debe ser numérico para cleaning en este MVP. Recibido=" + habitacionId, e);
        }

        Map<String, Object> body = Map.of(
                "referencia", "LIM-" + codigoReserva,
                "habitacionId", habitacionLong,
                "fecha", java.time.LocalDate.now(),
                "tipo", "LIMPIEZA",
                "estado", "PENDIENTE"
        );

        return restTemplate.postForEntity(CLEANING_BASE_URL, body, Map.class);
    }
}

