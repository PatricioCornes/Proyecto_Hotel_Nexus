package com.nexus.hospitality_reservation.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class IntegracionHabitacionService {

    private final RestTemplate restTemplate = new RestTemplate();

    // Rooms corre en 8081
    private static final String ROOMS_BASE_URL = "http://localhost:8081/habitaciones/asignar";

    public Map<String, Object> asignarHabitacionPorCodigoReserva(String codigoReserva) {
        // Request { codigoReserva, habitacionId }
        // MVP: habitacionId en tu modelo actual = se ignora en Rooms; se usa para future.
        Map<String, Object> body = Map.of(
                "codigoReserva", codigoReserva,
                "habitacionId", "AUTO"
        );

        ResponseEntity<Map> resp = restTemplate.postForEntity(ROOMS_BASE_URL, body, Map.class);
        return resp.getBody();
    }
}

