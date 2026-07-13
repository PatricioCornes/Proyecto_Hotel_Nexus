package com.Nexus_hospitality_payments.repositorio;

import com.Nexus_hospitality_payments.entidad.CargoAdicional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CargoAdicionalRepositorio extends JpaRepository<CargoAdicional, Long> {
    List<CargoAdicional> findByReservaCodigo(String reservaCodigo);
}
