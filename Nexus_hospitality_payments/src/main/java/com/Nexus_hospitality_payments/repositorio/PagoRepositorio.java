package com.Nexus_hospitality_payments.repositorio;

import com.Nexus_hospitality_payments.entidad.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagoRepositorio extends JpaRepository<Pago, Long> {
    Optional<Pago> findByReferencia(String referencia);
    boolean existsByReferencia(String referencia);
}

