package com.nexus.hospitality_reservation.service;

import com.nexus.hospitality_reservation.model.Reservacion;
import com.nexus.hospitality_reservation.repository.ReservacionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CheckoutOrchestratorService {

    private final ReservacionRepository reservacionRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final IntegracionCleaningService integracionCleaningService;

    private static final String ROOMS_CHECKOUT_BASE_URL = "http://localhost:8081/habitaciones/checkout";

    public CheckoutOrchestratorService(ReservacionRepository reservacionRepository,
                                         IntegracionCleaningService integracionCleaningService) {
        this.reservacionRepository = reservacionRepository;
        this.integracionCleaningService = integracionCleaningService;
    }

    @Transactional
    public Reservacion checkoutPorCodigoReserva(String codigoReserva) {
        Reservacion reservacion = reservacionRepository.findAll()
                .stream()
                .filter(r -> r.getCodigoReserva() != null && r.getCodigoReserva().equals(codigoReserva))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada con codigoReserva=" + codigoReserva));


        Map<String, Object> body = Map.of(
                "codigoReserva", codigoReserva,
                "habitacionId", reservacion.getHabitacionId()
        );

        ResponseEntity<Map> roomsResp = restTemplate.postForEntity(
                ROOMS_CHECKOUT_BASE_URL,
                body,
                Map.class
        );

        
        integracionCleaningService.crearTareaLimpieza(codigoReserva, reservacion.getHabitacionId());

        
        reservacion.setEstado("CHECK_OUT_REALIZADO");
        return reservacionRepository.save(reservacion);
    }
}

