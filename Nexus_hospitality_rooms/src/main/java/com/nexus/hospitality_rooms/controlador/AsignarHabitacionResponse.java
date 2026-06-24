package com.nexus.hospitality_rooms.controlador;

/**
 * Respuesta para la asignación de habitación.
 */
public record AsignarHabitacionResponse(
        String habitacionId,
        String estado
) {
}

