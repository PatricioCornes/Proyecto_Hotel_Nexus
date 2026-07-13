package com.Nexus_hospitality_payments.repositorio;

import com.Nexus_hospitality_payments.entidad.FacturaFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FacturaFinalRepositorio extends JpaRepository<FacturaFinal, Long> {
    Optional<FacturaFinal> findByReservaCodigo(String reservaCodigo);
}
