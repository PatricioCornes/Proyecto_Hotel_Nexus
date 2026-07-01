package com.Nexus_hospitality_restaurant.repositorio;

import com.Nexus_hospitality_restaurant.entidad.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepositorio extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByNombre(String nombre);
}

