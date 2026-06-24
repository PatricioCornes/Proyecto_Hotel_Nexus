package com.nexus.hospitality_rooms.servicio;

import com.nexus.hospitality_rooms.entidad.Habitacion;
import com.nexus.hospitality_rooms.repositorio.HabitacionRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HabitacionCheckoutService {

    private final HabitacionRepositorio habitacionRepositorio;

    public HabitacionCheckoutService(HabitacionRepositorio habitacionRepositorio) {
        this.habitacionRepositorio = habitacionRepositorio;
    }

    @Transactional
    public Habitacion marcarSucia(String habitacionId) {
        Long id;
        try {
            id = Long.valueOf(habitacionId);
        } catch (Exception e) {
            throw new IllegalArgumentException("habitacionId debe ser numérico en este MVP. Recibido=" + habitacionId, e);
        }

        Habitacion habitacion = habitacionRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada con id=" + habitacionId));

        habitacion.setEstado("Sucia");
        return habitacionRepositorio.save(habitacion);
    }
}

