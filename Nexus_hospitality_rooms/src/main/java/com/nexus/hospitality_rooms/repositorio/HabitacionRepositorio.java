package com.nexus.hospitality_rooms.repositorio;

import com.nexus.hospitality_rooms.entidad.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;

public interface HabitacionRepositorio extends JpaRepository<Habitacion, Long> {
    Optional<Habitacion> findByNumero(String numero);
    boolean existsByNumero(String numero);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Habitacion> findFirstByEstadoIgnoreCaseOrderByIdAsc(String estado);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Habitacion> findFirstByTipoIgnoreCaseAndEstadoIgnoreCaseOrderByIdAsc(String tipo, String estado);
}

