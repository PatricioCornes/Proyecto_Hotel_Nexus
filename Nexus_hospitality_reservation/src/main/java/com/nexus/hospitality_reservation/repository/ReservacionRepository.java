package com.nexus.hospitality_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexus.hospitality_reservation.model.Reservacion;

/**
 * Repositorio JPA para acceder a los datos de Reservación en la base de datos
 * Proporciona métodos CRUD automáticamente: save, findAll, findById, delete, etc.
 */
@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long>{
    // Los métodos CRUD estándar se heredan automáticamente de JpaRepository
}
