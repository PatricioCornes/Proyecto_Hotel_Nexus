package com.Nexus_hospitality_restaurant;

import com.Nexus_hospitality_restaurant.controlador.RestaurantControlador;
import com.Nexus_hospitality_restaurant.entidad.Restaurant;
import com.Nexus_hospitality_restaurant.repositorio.RestaurantRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RestaurantDataSeeder implements CommandLineRunner {

    private final RestaurantRepositorio restaurantRepositorio;

    public RestaurantDataSeeder(RestaurantRepositorio restaurantRepositorio) {
        this.restaurantRepositorio = restaurantRepositorio;
    }

    @Override
    public void run(String... args) {
        // Inserta un menu simple solo si no existe todavía
        restaurantRepositorio.findByNombre("Menu del Dia").ifPresentOrElse(
                r -> {},
                () -> {
                    Restaurant restaurant = Restaurant.builder()
                            .nombre("Menu del Dia")
                            .categoria("Hotel")
                            .capacidad(50)
                            .ubicacion("Planta Baja")
                            .activo(true)
                            .build();
                    restaurantRepositorio.save(restaurant);
                }
        );
    }
}

