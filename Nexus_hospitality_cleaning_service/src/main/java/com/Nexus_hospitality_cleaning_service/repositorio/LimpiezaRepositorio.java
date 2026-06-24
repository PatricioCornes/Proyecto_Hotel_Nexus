package com.Nexus_hospitality_cleaning_service.repositorio;

import com.Nexus_hospitality_cleaning_service.entidad.Limpieza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LimpiezaRepositorio extends JpaRepository<Limpieza, Long> {
    Optional<Limpieza> findByReferencia(String referencia);
    boolean existsByReferencia(String referencia);
}

