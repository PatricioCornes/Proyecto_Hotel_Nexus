package com.Nexus_hospitality_garage.repository;

import com.Nexus_hospitality_garage.model.UsoEstacionamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsoEstacionamientoRepository extends JpaRepository<UsoEstacionamiento, Long> {
    List<UsoEstacionamiento> findByReservaCodigo(String reservaCodigo);
}
