package com.nexus.hospitality_rooms.controlador;

import com.nexus.hospitality_rooms.entidad.Habitacion;
import com.nexus.hospitality_rooms.servicio.AsignacionHabitacionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habitaciones/asignar")
public class AsignarHabitacionController {

    private final AsignacionHabitacionService asignacionHabitacionService;

    public AsignarHabitacionController(AsignacionHabitacionService asignacionHabitacionService) {
        this.asignacionHabitacionService = asignacionHabitacionService;
    }

    /**
     * POST /habitaciones/asignar
     * Asigna automáticamente una habitación disponible.
     */
    @PostMapping
    public AsignarHabitacionResponse asignar(@Valid @RequestBody AsignarHabitacionRequest request) {
        Habitacion asignada = asignacionHabitacionService.asignarPorCodigoReserva(request.codigoReserva());
        return new AsignarHabitacionResponse(String.valueOf(asignada.getId()), asignada.getEstado());
    }
}

