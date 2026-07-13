package com.Nexus_hospitality_restaurant.servicio;

import com.Nexus_hospitality_restaurant.controlador.RestaurantRequest;
import com.Nexus_hospitality_restaurant.entidad.Restaurant;
import com.Nexus_hospitality_restaurant.repositorio.RestaurantRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServicio {

    private final RestaurantRepositorio restaurantRepositorio;

    public RestaurantServicio(RestaurantRepositorio restaurantRepositorio) {
        this.restaurantRepositorio = restaurantRepositorio;
    }

    @Transactional(readOnly = true)
    public List<Restaurant> listar() {
        return restaurantRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Restaurant obtenerPorId(Long id) {
        return restaurantRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant no encontrado con id=" + id));
    }

    @Transactional
    public Restaurant crear(RestaurantRequest request) {
        validarNombre(request.nombre(), null);

        Restaurant restaurant = Restaurant.builder()
                .nombre(request.nombre())
                .categoria(request.categoria())
                .capacidad(request.capacidad())
                .ubicacion(request.ubicacion())
                .activo(request.activo())
                .build();

        return restaurantRepositorio.save(restaurant);
    }

    @Transactional
    public Restaurant actualizar(Long id, RestaurantRequest request) {
        Restaurant existente = obtenerPorId(id);

        validarNombre(request.nombre(), existente.getId());

        existente.setNombre(request.nombre());
        existente.setCategoria(request.categoria());
        existente.setCapacidad(request.capacidad());
        existente.setUbicacion(request.ubicacion());
        existente.setActivo(request.activo());

        return restaurantRepositorio.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!restaurantRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Restaurant no encontrado con id=" + id);
        }
        restaurantRepositorio.deleteById(id);
    }

    private void validarNombre(String nombre, Long idActual) {
        Optional<Restaurant> posible = restaurantRepositorio.findByNombre(nombre);
        if (posible.isPresent()) {
            if (idActual == null || !posible.get().getId().equals(idActual)) {
                throw new IllegalStateException("Ya existe un restaurant con nombre=" + nombre);
            }
        }
    }
}

