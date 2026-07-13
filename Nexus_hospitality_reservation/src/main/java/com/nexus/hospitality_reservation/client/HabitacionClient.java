package com.nexus.hospitality_reservation.client;

import com.nexus.hospitality_reservation.dto.HabitacionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "nexus-rooms")
public interface HabitacionClient {
    @GetMapping("/habitaciones/{id}")
    HabitacionDTO obtenerHabitacion(@PathVariable("id") Long id);

    @PostMapping("/interno/habitaciones/asignar")
    HabitacionDTO asignarHabitacion(@RequestBody AsignacionRequest request);

    @PutMapping("/interno/habitaciones/{id}/estado")
    void cambiarEstado(@PathVariable Long id, @RequestBody EstadoRequest request);

    record AsignacionRequest(String tipo) {}
    record EstadoRequest(String estado) {}
}
