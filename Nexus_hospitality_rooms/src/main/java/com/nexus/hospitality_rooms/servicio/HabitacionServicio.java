package com.nexus.hospitality_rooms.servicio;

import com.nexus.hospitality_rooms.entidad.Habitacion;
import com.nexus.hospitality_rooms.controlador.HabitacionRequest;

import com.nexus.hospitality_rooms.repositorio.HabitacionRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionServicio {

    private final HabitacionRepositorio habitacionRepositorio;

    public HabitacionServicio(HabitacionRepositorio habitacionRepositorio) {
        this.habitacionRepositorio = habitacionRepositorio;
    }

    @Transactional(readOnly = true)
    public List<Habitacion> listar() {
        return habitacionRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Habitacion obtenerPorId(Long id) {
        return habitacionRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada con id=" + id));
    }

    @Transactional
    public Habitacion crear(HabitacionRequest request) {
        validarNumero(request.numero(), null);

        Habitacion habitacion = Habitacion.builder()
                .numero(request.numero())
                .tipo(request.tipo())
                .precioPorNoche(request.precioPorNoche())
                .estado(request.estado())
                .build();

        return habitacionRepositorio.save(habitacion);
    }

    @Transactional
    public Habitacion actualizar(Long id, HabitacionRequest request) {
        Habitacion existente = obtenerPorId(id);

        validarNumero(request.numero(), existente.getId());

        existente.setNumero(request.numero());
        existente.setTipo(request.tipo());
        existente.setPrecioPorNoche(request.precioPorNoche());
        existente.setEstado(request.estado());

        return habitacionRepositorio.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!habitacionRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Habitación no encontrada con id=" + id);
        }
        habitacionRepositorio.deleteById(id);
    }

    @Transactional
    public Habitacion asignarDisponible(String tipo) {
        Habitacion habitacion = (tipo == null || tipo.isBlank()
                ? habitacionRepositorio.findFirstByEstadoIgnoreCaseOrderByIdAsc("Disponible")
                : habitacionRepositorio.findFirstByTipoIgnoreCaseAndEstadoIgnoreCaseOrderByIdAsc(tipo, "Disponible"))
                .orElseThrow(() -> new IllegalStateException("No hay habitaciones disponibles"));
        habitacion.setEstado("Reservada");
        return habitacionRepositorio.save(habitacion);
    }

    @Transactional
    public void cambiarEstado(Long id, String estado) {
        Habitacion habitacion = obtenerPorId(id);
        habitacion.setEstado(estado);
        habitacionRepositorio.save(habitacion);
    }

    private void validarNumero(String numero, Long idActual) {
        Optional<Habitacion> posible = habitacionRepositorio.findByNumero(numero);
        if (posible.isPresent()) {
            if (idActual == null || !posible.get().getId().equals(idActual)) {
                throw new IllegalStateException("Ya existe una habitación con número=" + numero);
            }
        }
    }
}

