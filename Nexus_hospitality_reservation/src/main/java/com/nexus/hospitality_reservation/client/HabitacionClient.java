package com.nexus.hospitality_reservation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.nexus.hospitality_reservation.dto.HabitacionDTO; // Asegúrate de tener este DTO creado

@FeignClient(name = "rooms-service", url = "http://localhost:8081/habitaciones") // Cambia la URL según la configuración de tu microservicio de habitaciones
public interface HabitacionClient {

    @GetMapping("/{id}")
        HabitacionDTO obtenerHabitacion(@PathVariable("id") Integer id);
}
