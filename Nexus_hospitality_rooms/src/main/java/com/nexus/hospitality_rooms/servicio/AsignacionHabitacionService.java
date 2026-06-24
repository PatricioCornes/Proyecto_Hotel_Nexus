package com.nexus.hospitality_rooms.servicio;

import com.nexus.hospitality_rooms.entidad.Habitacion;
import com.nexus.hospitality_rooms.repositorio.HabitacionRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AsignacionHabitacionService {

    private final HabitacionRepositorio habitacionRepositorio;

    public AsignacionHabitacionService(HabitacionRepositorio habitacionRepositorio) {
        this.habitacionRepositorio = habitacionRepositorio;
    }

    @Transactional
    public Habitacion asignarPorCodigoReserva(String codigoReserva) {

        List<Habitacion> todas = habitacionRepositorio.findAll();
        Habitacion disponible = null;
        for (Habitacion h : todas) {
            if (h.getEstado() != null && h.getEstado().equalsIgnoreCase("DISPONIBLE")) {
                disponible = h;
                break;
            }
        }

        if (disponible == null) {
            throw new IllegalStateException("No hay habitaciones disponibles");
        }

        disponible.setEstado("Ocupada");
        return habitacionRepositorio.save(disponible);
    }
}

