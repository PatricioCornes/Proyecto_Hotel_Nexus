package com.nexus.hospitality_rooms.controlador;

import jakarta.validation.constraints.NotBlank;

/**
 * Request para check-out / marcar habitación como sucia.
 */
public record CheckoutRequest(
        @NotBlank String codigoReserva,
        @NotBlank String habitacionId
) {
}

