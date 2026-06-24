package com.Nexus_hospitality_restaurant.controlador;

import com.Nexus_hospitality_restaurant.entidad.Restaurant;
import com.Nexus_hospitality_restaurant.servicio.RestaurantServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantControlador {

    private final RestaurantServicio restaurantServicio;

    public RestaurantControlador(RestaurantServicio restaurantServicio) {
        this.restaurantServicio = restaurantServicio;
    }

    @GetMapping
    public List<Restaurant> listar() {
        return restaurantServicio.listar();
    }

    @GetMapping("/{id}")
    public Restaurant obtenerPorId(@PathVariable Long id) {
        return restaurantServicio.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant crear(@Valid @RequestBody RestaurantRequest request) {
        return restaurantServicio.crear(request);
    }

    @PutMapping("/{id}")
    public Restaurant actualizar(@PathVariable Long id, @Valid @RequestBody RestaurantRequest request) {
        return restaurantServicio.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        restaurantServicio.eliminar(id);
    }
}

