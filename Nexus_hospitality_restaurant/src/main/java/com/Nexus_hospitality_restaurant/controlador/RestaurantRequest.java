package com.Nexus_hospitality_restaurant.controlador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RestaurantRequest(
        @NotBlank String nombre,
        String categoria,
        @NotNull @PositiveOrZero Integer capacidad,
        String ubicacion,
        @NotNull Boolean activo
) {}

