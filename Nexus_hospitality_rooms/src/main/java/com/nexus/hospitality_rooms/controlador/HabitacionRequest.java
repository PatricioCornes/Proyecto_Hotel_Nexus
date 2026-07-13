package com.nexus.hospitality_rooms.controlador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

public record HabitacionRequest(
        @NotBlank @Size(max = 10) @Pattern(regexp = "[A-Za-z0-9-]+", message = "El numero solo admite letras, numeros y guion") String numero,
        @NotBlank @Size(max = 30) String tipo,
        @NotNull @PositiveOrZero @Digits(integer = 10, fraction = 2) BigDecimal precioPorNoche,
        @NotBlank @Pattern(regexp = "DISPONIBLE|RESERVADA|OCUPADA|SUCIA|EN_MANTENIMIENTO", message = "Estado de habitacion no valido") String estado
) {
}

