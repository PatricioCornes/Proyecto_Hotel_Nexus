package com.nexus.hospitality_rooms.repositorio;

import com.nexus.hospitality_rooms.entidad.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitacionRepositorio extends JpaRepository<Habitacion, Long> {
    Optional<Habitacion> findByNumero(String numero);
    boolean existsByNumero(String numero);
}

