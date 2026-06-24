package com.nexus.hospitality_rooms.controlador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Request para asignar una habitación a una reservación.
 */
public record AsignarHabitacionRequest(
        @NotBlank String codigoReserva,
        @NotBlank String habitacionId // en este MVP se usará como número/identificador que ya trae Reserva
) {
}

