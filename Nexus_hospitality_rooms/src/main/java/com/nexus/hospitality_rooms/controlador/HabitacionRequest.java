package com.nexus.hospitality_rooms.controlador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record HabitacionRequest(
        @NotBlank String numero,
        @NotBlank String tipo,
        @NotNull @PositiveOrZero BigDecimal precioPorNoche,
        @NotBlank String estado
) {
}

