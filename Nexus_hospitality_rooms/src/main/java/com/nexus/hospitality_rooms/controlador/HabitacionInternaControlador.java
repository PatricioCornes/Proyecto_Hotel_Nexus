package com.nexus.hospitality_rooms.controlador;

import com.nexus.hospitality_rooms.entidad.Habitacion;
import com.nexus.hospitality_rooms.servicio.HabitacionServicio;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interno/habitaciones")
public class HabitacionInternaControlador {

    private final HabitacionServicio servicio;

    public HabitacionInternaControlador(HabitacionServicio servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/asignar")
    public Habitacion asignar(@RequestBody AsignacionRequest request) {
        return servicio.asignarDisponible(request.tipo());
    }

    @PutMapping("/{id}/estado")
    public void cambiarEstado(@PathVariable Long id, @RequestBody EstadoRequest request) {
        servicio.cambiarEstado(id, request.estado());
    }

    public record AsignacionRequest(String tipo) {}
    public record EstadoRequest(String estado) {}
}
