package com.Nexus_hospitality_restaurant.repositorio;

import com.Nexus_hospitality_restaurant.entidad.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConsumoRepositorio extends JpaRepository<Consumo, Long> {
    List<Consumo> findByReservaCodigo(String reservaCodigo);
}
