package com.nexus.hospitality_rooms.controlador;

import com.nexus.hospitality_rooms.entidad.Habitacion;
import com.nexus.hospitality_rooms.servicio.HabitacionServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionControlador {

    private final HabitacionServicio habitacionServicio;

    public HabitacionControlador(HabitacionServicio habitacionServicio) {
        this.habitacionServicio = habitacionServicio;
    }

    @GetMapping
    public List<Habitacion> listar() {
        return habitacionServicio.listar();
    }

    @GetMapping("/{id}")
    public Habitacion obtenerPorId(@PathVariable Long id) {
        return habitacionServicio.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Habitacion crear(@Valid @RequestBody HabitacionRequest request) {
        return habitacionServicio.crear(request);
    }

    @PutMapping("/{id}")
    public Habitacion actualizar(@PathVariable Long id, @Valid @RequestBody HabitacionRequest request) {
        return habitacionServicio.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        habitacionServicio.eliminar(id);
    }
}

