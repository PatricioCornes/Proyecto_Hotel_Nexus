package com.Nexus_hospitality_restaurant.controlador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record RestaurantRequest(
        @NotBlank @Size(max = 100) String nombre,
        @Size(max = 60) String categoria,
        @NotNull @PositiveOrZero Integer capacidad,
        @Size(max = 150) String ubicacion,
        @NotNull Boolean activo
) {}

