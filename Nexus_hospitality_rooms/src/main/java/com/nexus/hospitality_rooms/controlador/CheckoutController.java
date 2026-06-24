package com.nexus.hospitality_rooms.controlador;

import com.nexus.hospitality_rooms.entidad.Habitacion;
import com.nexus.hospitality_rooms.servicio.HabitacionCheckoutService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habitaciones/checkout")
public class CheckoutController {

    private final HabitacionCheckoutService habitacionCheckoutService;

    public CheckoutController(HabitacionCheckoutService habitacionCheckoutService) {
        this.habitacionCheckoutService = habitacionCheckoutService;
    }

    /**
     * POST /habitaciones/checkout
     * Marca la habitación como SUCIA y deja lista para generar tarea de limpieza.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Habitacion marcarSucia(@Valid @RequestBody CheckoutRequest request) {
        return habitacionCheckoutService.marcarSucia(request.habitacionId());
    }
}

