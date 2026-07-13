package com.nexus.hospitality_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexus.hospitality_reservation.model.Reservacion;
import java.util.Optional;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long>{
    Optional<Reservacion> findByCodigoReserva(String codigoReserva);
}
